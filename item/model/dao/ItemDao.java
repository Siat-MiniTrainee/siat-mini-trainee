package item.model.dao;

import common.util.JDBCUtil;
import item.model.domain.ItemInfoDto;
import item.model.domain.ItemCategory;
import item.model.domain.ItemType;
import item.model.domain.ItemUseDto;
import state.model.domain.StatType;
import state.model.domain.StateUpdateInfoDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private static volatile ItemDao instance = new ItemDao();
    private JDBCUtil db;

    private ItemDao() {
        db = JDBCUtil.getInstance();
    }

    public static ItemDao getInstance() {
        return instance;
    }
    public List<ItemInfoDto> selectPlayerItemsAndStatChanges(int playerId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;

    String sql = "SELECT i.item_id AS id, i.item_name AS name, ic.category_name AS category, " +
                 "it.type_name AS type, i.item_description AS description, i.cost, " +
                 "sc.change_value, sc.change_type, s.stat_name " +
                 "FROM inventory inv " +
                 "JOIN item i ON inv.item_id = i.item_id " +
                 "JOIN item_category ic ON i.category_id = ic.category_id " +
                 "JOIN item_type it ON i.item_type = it.item_type " +
                 "LEFT JOIN item_stat_change isc ON i.item_id = isc.item_id " +
                 "LEFT JOIN stat_change sc ON isc.change_id = sc.change_id " +
                 "LEFT JOIN stat s ON sc.stat_id = s.stat_id " +
                 "WHERE inv.player_id = ?";

        List<ItemInfoDto> resultList = new ArrayList<>();
        try {
            conn = db.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, playerId);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                ItemInfoDto item = ItemInfoDto.builder()
                    .id(rset.getInt("id"))
                    .name(rset.getString("name"))
                    .category(ItemCategory.valueOf(rset.getString("category")))
                    .type(ItemType.valueOf(rset.getString("type")))
                    .description(rset.getString("description"))
                    .cost(rset.getDouble("cost"))
                    .build();

                // 여기서 아이템에 연결된 스탯 변경 정보를 처리합니다.
                // ItemInfoDto에 필요한 필드를 추가하여 스탯 변경 정보를 포함할 수 있게 확장해야 합니다.

                resultList.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        return resultList; // 적절한 오류 처리가 필요합니다.
    }

    public ItemUseDto selectPlayerItemStatChanges(int playerId, int itemId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rset = null;

    String sql = "SELECT i.item_id AS id, i.item_name AS name, ic.category_name AS category, " +
                 "it.type_name AS type, i.item_description AS description, i.cost, " +
                 "sc.change_value, sc.change_type, s.stat_name " +
                 "FROM inventory inv " +
                 "JOIN item i ON inv.item_id = i.item_id " +
                 "JOIN item_category ic ON i.category_id = ic.category_id " +
                 "JOIN item_type it ON i.item_type = it.item_type " +
                 "LEFT JOIN item_stat_change isc ON i.item_id = isc.item_id " +
                 "LEFT JOIN stat_change sc ON isc.change_id = sc.change_id " +
                 "LEFT JOIN stat s ON sc.stat_id = s.stat_id " +
                 "WHERE inv.player_id = ? AND i.item_id = ?";

    ItemUseDto itemUseDto = null;
    try {
        conn = db.getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, playerId);
        pstmt.setInt(2, itemId);
        rset = pstmt.executeQuery();

        if (rset.next()) {
            String itemName = rset.getString("name");
            List<StateUpdateInfoDto> stateChanges = new ArrayList<>();

            do {
                String statName = rset.getString("stat_name");
                StatType statType = StatType.fromString(statName);

                if (statType != null) { // If statType is recognized
                    double changeValue = rset.getDouble("change_value");
                    String changeType = rset.getString("change_type");
                    
                    stateChanges.add(new StateUpdateInfoDto(statType.getTypeName(), changeValue, changeType));
                }
                } while (rset.next());

                itemUseDto = ItemUseDto.builder()
                .itemId(itemId)
                .itemName(itemName)
                .statChange(StateUpdateInfoDto.builder()
                            .hp(itemId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.close(rset, pstmt, conn);
        }
        
        return itemUseDto;
    }


    public ItemInfoDto getItemInfo(int itemId) {
        String sql = "SELECT id, name, category, type, description, cost FROM items WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return ItemInfoDto.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .category(ItemCategory.valueOf(rs.getString("category")))
                        .type(ItemType.valueOf(rs.getString("type")))
                        .description(rs.getString("description"))
                        .cost(rs.getDouble("cost"))
                        .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Error handling 필요
    }
}