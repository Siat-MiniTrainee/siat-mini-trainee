package shop.service;

import shop.model.dao.ShopDao;
import shop.model.domain.ShopItemFilterDto;
import shop.model.domain.ShopItemSortDto;
import item.model.domain.ItemInfoDto;
import item.model.domain.ItemCategory;
import item.model.domain.ItemType;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShopService {
    private static volatile ShopService instance = new ShopService();
    private ShopDao shopDao;

    private ShopService() {
        shopDao = ShopDao.getInstance();
    }

    public static ShopService getInstance() {
        return instance;
    }
    public List<ItemInfoDto> getItems(int playerId) {
        return shopDao.getAllItems();
    }

 public List<ItemInfoDto> getFilteredItems(int playerId, ShopItemFilterDto filterCriteria) {
    List<ItemInfoDto> allItems = shopDao.getAllItems();

    if (filterCriteria == null) {
        return allItems;
    }
    // 필터링 적용
    return allItems.stream()
        .filter(item -> (filterCriteria.getItemName() != null &&
                        item.getName().contains(filterCriteria.getItemName()))|| 
                        (item.getCategory()!= null&&item.getCategory().equals( filterCriteria.getItemCategory())))
        .collect(Collectors.toList());
    }

    public List<ItemInfoDto> getFilteredAndSortedItems(
            int playerId,
            ShopItemFilterDto filterCriteria,
            ShopItemSortDto sortCriteria) {
        
        List<ItemInfoDto> allItems = shopDao.getAllItems();

        // Apply filtering
        if (filterCriteria != null) {
            allItems = allItems.stream()
                .filter(item -> (filterCriteria.getItemName() != null &&
                        item.getName().contains(filterCriteria.getItemName())) ||
                        (filterCriteria.getItemCategory() != null &&
                        item.getCategory().equals(filterCriteria.getItemCategory())))
                .collect(Collectors.toList());
        }

        // Apply sorting
        if (sortCriteria != null && sortCriteria.getSortBy() != null) {
            try {
                Comparator<ItemInfoDto> comparator;

                switch (sortCriteria.getSortBy()) {
                    case "name":
                        comparator = Comparator.comparing(ItemInfoDto::getName);
                        break;
                    case "category":
                        comparator = Comparator.comparing(ItemInfoDto::getCategory);
                        break;
                    case "cost":
                        comparator = Comparator.comparing(ItemInfoDto::getCost);
                        break;
                    // Add more cases for other fields like price, etc.
                    default:
                        return List.of(); // Return empty list for invalid sort field
                }

                if (!sortCriteria.isAscending()) {
                    comparator = comparator.reversed();
                }

                allItems = allItems.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());
            } catch (Exception e) {
                return List.of(); // Return empty list if sorting fails
            }
        }

        return allItems;
    }
}