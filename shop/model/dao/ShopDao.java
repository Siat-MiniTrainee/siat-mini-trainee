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

    // 단순히 모든 아이템 정보를 가져오는 메소드
    public List<ItemInfoDto> getAllItems() {
        List<ItemInfoDto> items = new ArrayList<>();
        String sql = "SELECT id, name, category_id, item_type, description, cost FROM item";
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ItemCategory category = ItemCategory.valueOf(rs.getString("category_id"));
                ItemType type = ItemType.valueOf(rs.getString("item_type"));
                ItemInfoDto item = ItemInfoDto.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .category(category)
                    .type(type)
                    .description(rs.getString("description"))
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