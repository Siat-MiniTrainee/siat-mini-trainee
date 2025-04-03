package time.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.util.JDBCUtil;
import quiz.model.domain.QuizDto;
import quiz.model.domain.QuizType;

public class TimeDao {
    private static volatile TimeDao instance = new TimeDao();
    private JDBCUtil db;

    private TimeDao() {
        db = JDBCUtil.getInstance();
    }
    public static TimeDao getInstance() {
        return instance;
    }
    public int getPlayerTime(int playerId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = "SELECT PLAYER_TIME FROM PLAYER_TIME WHERE PLAYER_ID = ?";
        int idx = 1;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playerId);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                return rset.getInt(idx++);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        return -1;
    }

    
    public int updatePlayerTime(int playerId, int newTime) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE PLAYER_TIME SET PLAYER_TIME = ? WHERE PLAYER_ID = ?";
        int idx = 1;
        int result = 0;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(idx++, playerId);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close( pstmt, conn);
        }
        return result;
    }

}