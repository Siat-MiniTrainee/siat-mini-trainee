package state.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JDBCUtil;
import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateRequestDto;

public class StateDao {
    private static volatile StateDao instance = new StateDao();
    private String PLAYER_TABLE_NAME = "PLAYER";
    private String PLAYER_DETAIL_TABLE_NAME = "player_detail";

    private JDBCUtil db;
    private StateDao() {
        db= JDBCUtil.getInstance();
    }

    public static StateDao getInstance() {
        return instance;
    }
    public int insertPlayer(StateResponseDto player) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;
        String sql = "INSERT INTO " + PLAYER_TABLE_NAME + " (player_id, player_name, max_mp, max_hp, hp, mp, int, str, money) "
                   + "VALUES ("+PLAYER_TABLE_NAME+"_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idx=1;
        int result = 0;
        int playerId=-1;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    
            pstmt.setString(idx++, player.getPlayerName());
            pstmt.setDouble(idx++, player.getMaxMp());
            pstmt.setDouble(idx++, player.getMaxHp());
            pstmt.setDouble(idx++, player.getHp());
            pstmt.setDouble(idx++, player.getMp());
            pstmt.setDouble(idx++, player.getIntelligence());
            pstmt.setDouble(idx++, player.getStrength());
            pstmt.setDouble(idx++, player.getMoney());
    
            result = pstmt.executeUpdate();
            if(result>0){
                generatedKeys =pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    playerId = generatedKeys.getInt(1);
                }
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(pstmt, conn);
        }
    
        return playerId;
    }
    public int insertPlayerDetail(StateResponseDto player) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO "+PLAYER_DETAIL_TABLE_NAME+" ("+PLAYER_DETAIL_TABLE_NAME+"_SEQ.NEXTVAL, player_id, player_name, score) "
                   + "VALUES ( ?, ?, ?)";
        int idx=1;
        int result = 0;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
    
            pstmt.setInt(idx++, player.getPlayerId());
            pstmt.setString(idx++, player.getPlayerName());
            pstmt.setDouble(idx++, 0); 
    
            result = pstmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(pstmt, conn);
        }
    
        return result;
    }
    public int updatePlayerState(StateUpdateRequestDto updatedState) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE " + PLAYER_TABLE_NAME + " SET "
                + "hp = ?, mp = ?, int = ?, str = ?, money = ? "
                + "WHERE player_id = ?";
    
        int result = 0;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
    
            int idx = 1;
            pstmt.setDouble(idx++, updatedState.getHp());
            pstmt.setDouble(idx++, updatedState.getMp());
            pstmt.setDouble(idx++, updatedState.getIntelligence());
            pstmt.setDouble(idx++, updatedState.getStrength());
            pstmt.setDouble(idx++, updatedState.getMoney());
    
            pstmt.setDouble(idx++, updatedState.getPlayerId());
    
            result = pstmt.executeUpdate();
    
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(null, pstmt, conn);
        }
    
        return result;
    }
    public List<StateResponseDto> selectPlayerAllState(){
        Connection conn=null;
        PreparedStatement pstmt =null;
        ResultSet rset = null;

        String sql = "select player_id, player_name,max_hp,max_mp,hp,mp,int,str,money from "
        +PLAYER_TABLE_NAME;

        List<StateResponseDto> resultList = new ArrayList<>();
        try {

            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            rset = pstmt.executeQuery();

            while (rset.next()){
                StateResponseDto item=StateResponseDto.builder()
                .playerId(rset.getInt("player_id"))
                .playerName(rset.getString("player_name"))
                .maxHp(rset.getDouble("max_hp"))
                .maxMp(rset.getDouble("max_mp"))
                .hp(rset.getDouble("hp"))
                .mp(rset.getDouble("mp"))
                .intelligence(rset.getDouble("int"))
                .strength(rset.getDouble("str"))
                .money(rset.getDouble("money"))
                .build();
                resultList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset,pstmt,conn);
        }
        return resultList;
        
    }
    public Optional<StateResponseDto> selectPlayerOneState(int playerId) {
        Connection conn=null;
        PreparedStatement pstmt =null;
        ResultSet rset = null;

        String sql = "select player_name,max_hp,max_mp,hp,mp,int,str,money from "
        +PLAYER_TABLE_NAME
        +" where player_id = ?";

        int idx =1;
        Optional<StateResponseDto> result = Optional.empty();
        try {

            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(idx++, playerId);
            rset = pstmt.executeQuery();

            if(rset.next()){
                result =Optional.of(StateResponseDto.builder()
                .playerId(playerId)
                .maxHp(rset.getDouble("max_hp"))
                .maxMp(rset.getDouble("max_mp"))
                .hp(rset.getDouble("hp"))
                .mp(rset.getDouble("mp"))
                .intelligence(rset.getDouble("int"))
                .strength(rset.getDouble("str"))
                .money(rset.getDouble("money"))
                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset,pstmt,conn);
        }
        return result;
        
    }
    
}
