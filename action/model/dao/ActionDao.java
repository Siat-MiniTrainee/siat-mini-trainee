package action.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import action.model.domain.ActionInfoDto;
import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import common.util.JDBCUtil;
import state.model.domain.StateUpdateInfoDto;

public class ActionDao {
    private static volatile ActionDao instance = new ActionDao();
    private JDBCUtil db;
    private ActionDao() {
        db= JDBCUtil.getInstance();
    }

    public static ActionDao getInstance() {
        return instance;
    }    

    public ActionInfoDto selectAction(ActionType type) {
        ActionInfoDto dto = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
    
        try {
            conn = db.getConnection();
    
            String sql = "SELECT " +
                         "  a.ACTION_ID AS ACTION_ID, " +
                         "  a.ACTION_NAME AS ACTION_NAME, " +
                         "  MAX(CASE WHEN s.STAT_NAME = 'HP' THEN sc2.CHANGE_VALUE END) AS HP, " +
                         "  MAX(CASE WHEN s.STAT_NAME = 'MP' THEN sc2.CHANGE_VALUE END) AS MP, " +
                         "  MAX(CASE WHEN s.STAT_NAME = 'INT' THEN sc2.CHANGE_VALUE END) AS INT, " +
                         "  MAX(CASE WHEN s.STAT_NAME = 'STR' THEN sc2.CHANGE_VALUE END) AS STR, " +
                         "  MAX(CASE WHEN s.STAT_NAME = 'MONEY' THEN sc2.CHANGE_VALUE END) AS MONEY " +
                         "FROM action a " +
                         "JOIN ACTION_STAT_CHANGE sc1 ON a.ACTION_ID = sc1.ACTION_ID " +
                         "JOIN STAT_CHANGE sc2 ON sc1.CHANGE_ID = sc2.CHANGE_ID " +
                         "JOIN STAT s ON s.STAT_ID = sc2.STAT_ID " +
                         "WHERE a.ACTION_NAME = ? " +
                         "GROUP BY a.ACTION_ID, a.ACTION_NAME " +
                         "ORDER BY a.ACTION_ID";
    
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, type.name());
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                StateUpdateInfoDto stateUpdate = StateUpdateInfoDto.builder()
                    .hp(rs.getDouble("HP"))
                    .mp(rs.getDouble("MP"))
                    .money(rs.getDouble("MONEY"))
                    .intelligence(rs.getDouble("INT"))
                    .strength(rs.getDouble("STR"))
                    .build();
    
                dto = ActionInfoDto.builder()
                    .actionName(rs.getString("ACTION_NAME"))
                    .actionType(type)
                    .stageChange(stateUpdate)
                    .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rs, pstmt, conn);
        }
    
        return dto;
    }

    public List<ActionInfoDto> selectActionAll() {
        List<ActionInfoDto> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = db.getConnection(); // DB 연결 객체 가져오기

            String sql = "SELECT " +
                        "  a.ACTION_ID, " +
                        "  a.ACTION_NAME, " +
                        "  MAX(CASE WHEN s.STAT_NAME = 'HP' THEN sc2.CHANGE_VALUE END) AS HP, " +
                        "  MAX(CASE WHEN s.STAT_NAME = 'MP' THEN sc2.CHANGE_VALUE END) AS MP, " +
                        "  MAX(CASE WHEN s.STAT_NAME = 'INT' THEN sc2.CHANGE_VALUE END) AS INT, " +
                        "  MAX(CASE WHEN s.STAT_NAME = 'STR' THEN sc2.CHANGE_VALUE END) AS STR, " +
                        "  MAX(CASE WHEN s.STAT_NAME = 'MONEY' THEN sc2.CHANGE_VALUE END) AS MONEY " +
                        "FROM action a " +
                        "JOIN ACTION_STAT_CHANGE sc1 ON a.ACTION_ID = sc1.ACTION_ID " +
                        "JOIN STAT_CHANGE sc2 ON sc1.CHANGE_ID = sc2.CHANGE_ID " +
                        "JOIN STAT s ON s.STAT_ID = sc2.STAT_ID " +
                    "GROUP BY a.ACTION_ID, a.ACTION_NAME, a.ACTION_TYPE ";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // 내부의 StateUpdateInfoDto 생성
                StateUpdateInfoDto stateUpdate = StateUpdateInfoDto.builder()
                    .hp(rs.getDouble("HP"))
                    .mp(rs.getDouble("MP"))    
                    .money(rs.getDouble("MONEY"))
                    .intelligence(rs.getDouble("INT")) 
                    .strength(rs.getDouble("STR"))      
                    .build();

                // 외부의 ActionInfoDto 생성
                ActionInfoDto dto = ActionInfoDto.builder()
                    .actionName(rs.getString("ACTION_NAME"))                   
                    .actionType(ActionType.valueOf(rs.getString("ACTION_NAME")))
                    .stageChange(stateUpdate)                                  
                    .build();

                list.add(dto); // 리스트에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 처리
        } finally {
            db.close(rs, pstmt, conn); // 리소스 해제
        }

        return list; // 결과 리스트 반환
    }
    // 특정 액션에 대한 스탯 변경정보를 조회하는 메서드
    public StateUpdateInfoDto selectActionStatChanges(ActionType actionType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StateUpdateInfoDto stateUpdate = null;
    
        try {
            conn = db.getConnection();
            
            String sql = "SELECT " + //
                         "  MAX(CASE WHEN s.STAT_NAME = 'HP' THEN sc2.CHANGE_VALUE END) AS HP, " + //
                         "  MAX(CASE WHEN s.STAT_NAME = 'MP' THEN sc2.CHANGE_VALUE END) AS MP, " + //
                         "  MAX(CASE WHEN s.STAT_NAME = 'INT' THEN sc2.CHANGE_VALUE END) AS INT, " + //
                         "  MAX(CASE WHEN s.STAT_NAME = 'STR' THEN sc2.CHANGE_VALUE END) AS STR, " + //
                         "  MAX(CASE WHEN s.STAT_NAME = 'MONEY' THEN sc2.CHANGE_VALUE END) AS MONEY " + //
                         "FROM action a " + //
                         "JOIN ACTION_STAT_CHANGE sc1 ON a.ACTION_ID = sc1.ACTION_ID " + //
                         "JOIN STAT_CHANGE sc2 ON sc1.CHANGE_ID = sc2.CHANGE_ID " + //
                         "JOIN STAT s ON s.STAT_ID = sc2.STAT_ID " + //
                         "WHERE a.ACTION_NAME = ? " + //
                         "GROUP BY a.ACTION_ID, a.ACTION_NAME";
    
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actionType.name());
            rs = pstmt.executeQuery();
    
            if (rs.next()) {
                stateUpdate = StateUpdateInfoDto.builder()
                    .hp(rs.getDouble("HP"))
                    .mp(rs.getDouble("MP"))
                    .money(rs.getDouble("MONEY"))
                    .intelligence(rs.getDouble("INT"))
                    .strength(rs.getDouble("STR"))
                    .build();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            db.close(rs, pstmt, conn);
        }
    
        return stateUpdate;
    }



}



