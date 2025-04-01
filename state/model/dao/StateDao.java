package state.model.dao;

import util.JDBCUtil;

public class StateDao {
    private static volatile StateDao instance = new StateDao();
    private JDBCUtil db;
    private StateDao() {
        db= JDBCUtil.getInstance();
    }

    public static StateDao getInstance() {
        return instance;
    }
    
}
