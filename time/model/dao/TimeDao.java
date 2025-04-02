package time.model.dao;

import common.util.JDBCUtil;

public class TimeDao {
    private static volatile TimeDao instance = new TimeDao();
    private JDBCUtil db;
    private TimeDao() {
        db= JDBCUtil.getInstance();
    }

    public static TimeDao getInstance() {
        return instance;
    }
    
}
