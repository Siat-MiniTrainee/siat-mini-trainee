package quiz.model.dao;

import util.JDBCUtil;

public class QuizDao {
    private static volatile QuizDao instance = new QuizDao();
    private JDBCUtil db;
    private QuizDao() {
        db= JDBCUtil.getInstance();
    }

    public static QuizDao getInstance() {
        return instance;
    }
}
