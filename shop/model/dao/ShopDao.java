package shop.model.dao;

import common.util.JDBCUtil;

public class ShopDao {
    private static volatile ShopDao instance = new ShopDao();
    private JDBCUtil db;
    private ShopDao() {
        db= JDBCUtil.getInstance();
    }

    public static ShopDao getInstance() {
        return instance;
    }
}
