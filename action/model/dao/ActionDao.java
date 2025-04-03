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
        ActionInfoDto dto;
        Connection conn;
        PreparedStatement pstmt;
        ResultSet         rs    = null;
        
        try {
            conn = db.getConnection();
            
            String sql =    "SELECT SC.STAT_TYPE, SC.DELTA " + 
                            "FROM ACTION a " +
                            "JOIN ACTION_STAT_CHANGE SC ON A.ACTION_ID = SC.STAT_CHANGE_ID "+
                            "WHERE A.ACTION_NAME = ? ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, type.name());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                
            }
        } finally {
            db.close(rs, pstmt, conn);
        }

        return dto;
    }

    public List<ActionInfoDto> selectActionAll(){
        List<ActionInfoDto> list = new ArrayList<>();
        Connection        conn  = null;
        PreparedStatement pstmt = null;
        ResultSet         rs    = null;
        
        Map<String, Integer> statChanges = new HashMap<>();

        try {
            conn = db.getConnection();
            
            String sql =    "SELECT SC.STAT_TYPE, SC.DELTA " + 
                            "FROM ACTION a " +
                            "JOIN ACTION_STAT_CHANGE SC ON A.ACTION_ID = SC.STAT_CHANGE_ID "+
                            "WHERE A.ACTION_NAME = ? ";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actionType);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                statChanges.put(rs.getString("stat_type"), rs.getInt("delta"));
            }
        } finally {
            db.close(rs, pstmt, conn);
        }

        return statChanges;
    }

    // 특정 액션에 대한 스탯 변경정보를 조회하는 메서드
    public Map<String, Integer> getActionStatChanges(String actionType) throws SQLException {
        Connection        conn  = null;
        PreparedStatement pstmt = null;
        ResultSet         rs    = null;
        
        Map<String, Integer> statChanges = new HashMap<>();

        try {
            conn = db.getConnection();
            
            String sql = "SELECT sc.stat_type, sc.delta " +
                         "FROM action a " +
                         "JOIN action_stat_change sc ON a.action_id = sc.action_id " +
                         "WHERE a.action_name = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, actionType);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                statChanges.put(rs.getString("stat_type"), rs.getInt("delta"));
            }
        } finally {
            db.close(rs, pstmt, conn);
        }

        return statChanges;
    }

    // 액션 수행 후 로그를 기록하는 메서드
    public void logActionResult(int playerId, String actionType, boolean success) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = db.getConnection();
            String sql = "INSERT INTO action_log (player_id, action_name, success, timestamp) " +
                         "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playerId);
            pstmt.setString(2, actionType);
            pstmt.setBoolean(3, success);
            pstmt.executeUpdate();
        } finally {
            db.close(pstmt, conn);
        }
    }
}



