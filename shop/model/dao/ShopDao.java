package shop.model.dao;

import common.util.JDBCUtil;
import item.model.domain.ItemInfoDto;
import item.model.domain.ItemCategory;
import item.model.domain.ItemType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDao {
    private static volatile ShopDao instance = new ShopDao();
    private JDBCUtil db;

    private ShopDao() {
        db = JDBCUtil.getInstance();
    }

    public static ShopDao getInstance() {
        return instance;
    }

    public List<ItemInfoDto> getAllItems() {
        List<ItemInfoDto> items = new ArrayList<>();
        String sql = "SELECT item_id, item_name, category_name, type_name, ITEM_DESCRIPTION , cost FROM item i"+
        " JOIN item_category ic on i.category_id= ic.category_id"+
        " JOIN item_type it on i.item_type=it.item_type";
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ItemCategory category = ItemCategory.valueOf(rs.getString("category_name").toUpperCase());
                ItemType type = ItemType.valueOf(rs.getString("type_name").toUpperCase());
                ItemInfoDto item = ItemInfoDto.builder()
                    .id(rs.getInt("item_id"))
                    .name(rs.getString("item_name"))
                    .category(category)
                    .type(type)
                    .description(rs.getString("ITEM_DESCRIPTION"))
                    .cost(rs.getDouble("cost"))
                    .build();
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}