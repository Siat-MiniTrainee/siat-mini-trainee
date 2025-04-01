package item.model.dao;

import util.JDBCUtil;

public class ItemDao {
    private static volatile ItemDao instance = new ItemDao();
    private JDBCUtil db;
    private ItemDao() {
        db= JDBCUtil.getInstance();
    }

    public static ItemDao getInstance() {
        return instance;
    }
}
