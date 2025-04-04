package quiz.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import common.util.JDBCUtil;
import quiz.model.domain.QuizConfirmResponseDto;
import quiz.model.domain.QuizDto;
import quiz.model.domain.QuizResponseDto;
import quiz.model.domain.QuizType;

public class QuizDao {
    private static volatile QuizDao instance = new QuizDao();
    private JDBCUtil db;
    private QuizDao() {
        db= JDBCUtil.getInstance();
    }

    public static QuizDao getInstance() {
        return instance;
    }

    // 특정 난이도에 해당하는 퀴즈 리스트를 반환하는 메서드
    public List<QuizDto> selectQuizzesByLevel(int level) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = "SELECT quiz_id, content, quiz_type_name FROM quiz q "
                + "JOIN quiz_type qt on q.quiz_type_id=qt.quiz_type_id "
                + "WHERE quiz_level = ?";
        List<QuizDto> resultList = new ArrayList<>();
        int idx = 1;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, level);
            rset = pstmt.executeQuery();
            while (rset.next()) {

                QuizDto quiz = QuizDto.builder()
                        .quizId(rset.getInt(1))
                        .content(rset.getString(2))
                        .quizType(QuizType.valueOf(rset.getString(3).toUpperCase()))
                        .build();
                resultList.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        return resultList;
    }
    public List<QuizDto> selectQuizzesByTypeAndLevel(QuizType type, int level) {
        

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = "SELECT quiz_id, content, quiz_type_name FROM quiz q "
                   + "JOIN quiz_type qt ON q.quiz_type_id = qt.quiz_type_id "
                   + "WHERE quiz_level = ? AND quiz_type_name = ?";
        List<QuizDto> resultList = new ArrayList<>();
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, level);
            pstmt.setString(2, type.name()); 
            rset = pstmt.executeQuery();

            while (rset.next()) {
                QuizDto quiz = QuizDto.builder()
                        .quizId(rset.getInt(1))
                        .content(rset.getString(2))
                        .quizType(QuizType.valueOf(rset.getString(3).toUpperCase()))
                        .build();
                resultList.add(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        return resultList;
    }

    public Optional<QuizConfirmResponseDto> selectQuizAnswer(int quizId, String answer) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        Optional<QuizConfirmResponseDto> result = Optional.empty();
        String sql = "SELECT q.quiz_id, " +
                "q.content, " +
                "q.answer AS answer, " +
                "q.explain AS explanation, " +
                "qt.quiz_type_name AS quiz_type, " +
                "q.quiz_level AS quiz_level " +
                "FROM quiz q " +
                "JOIN quiz_type qt ON q.quiz_type_id = qt.quiz_type_id " +
                "WHERE q.quiz_id = ? AND q.answer = ?";
        int idx = 1;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(idx++, quizId);
            pstmt.setString(idx++, answer);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                QuizConfirmResponseDto quiz = QuizConfirmResponseDto.builder()
                        .result("성공")
                        .explanation(rset.getString("explanation"))
                        .answer(rset.getString("answer"))
                        .quizType(QuizType.valueOf(rset.getString("quiz_type").toUpperCase()))
                        .level(rset.getInt("quiz_level"))
                        .build();
                result = Optional.of(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        return result;
    }
    public Optional<QuizConfirmResponseDto> selectQuiz(int quizId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        Optional<QuizConfirmResponseDto> result = Optional.empty();
        String sql = "SELECT q.quiz_id, " +
                "q.content, " +
                "q.answer, " +
                "q.explain AS explanation, " +
                "qt.quiz_type_name AS quiz_type, " +
                "q.quiz_level AS level " +
                "FROM quiz q " +
                "JOIN quiz_type qt ON q.quiz_type_id = qt.quiz_type_id " +
                "WHERE q.quiz_id = ?";
        int idx = 1;
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(idx++, quizId);
            rset = pstmt.executeQuery();

            if (rset.next()) {
                QuizConfirmResponseDto quiz = QuizConfirmResponseDto.builder()
                .result("실패") 
                .explanation(rset.getString("explanation"))
                .answer(rset.getString("answer"))
                .quizType(QuizType.valueOf(rset.getString("quiz_type").toUpperCase()))
                .level(rset.getInt("level"))
                .build();
                result = Optional.of(quiz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        return result;
    }
    
}
