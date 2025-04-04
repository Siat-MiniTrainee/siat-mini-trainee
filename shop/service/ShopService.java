package shop.service;

import shop.model.dao.ShopDao;
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

    public List<ItemInfoDto> getSortedItems(String sortOption) {
        List<ItemInfoDto> items = shopDao.getAllItems();
        
        switch (sortOption.toLowerCase()) {
            case "name":
                return items.stream()
                            .sorted(Comparator.comparing(ItemInfoDto::getName))
                            .collect(Collectors.toList());
            case "cost":
                return items.stream()
                            .sorted(Comparator.comparing(ItemInfoDto::getCost))
                            .collect(Collectors.toList());
            case "category":
                return items.stream()
                            .sorted(Comparator.comparing(ItemInfoDto::getCategory))
                            .collect(Collectors.toList());
            case "type":
                return items.stream()
                            .sorted(Comparator.comparing(ItemInfoDto::getType))
                            .collect(Collectors.toList());
            default:
                return items; 
        }
    }
}