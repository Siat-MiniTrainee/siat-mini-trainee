package action.model.dao;

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
}
