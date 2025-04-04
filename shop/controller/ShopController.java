package shop.controller;

import shop.service.ShopService;
import item.model.domain.ItemInfoDto;
import shop.model.domain.ShopItemFilterDto;
import shop.model.domain.ShopItemSortDto;

import java.util.List;

public class ShopController {
    private ShopService shopService;

    public ShopController() {
        shopService = ShopService.getInstance();
    }
    public List<ItemInfoDto> getItems(int playerId){
        return shopService.getItems(playerId);
    }

    public List<ItemInfoDto> getFilteredItems(int playerId, ShopItemFilterDto filterCriteria) {
        return shopService.getFilteredItems(playerId, filterCriteria);
    }
    public List<ItemInfoDto> getShopOrderList(int playerId, ShopItemFilterDto filterCriteria, ShopItemSortDto sortDto) {
        return shopService.getFilteredAndSortedItems(playerId, filterCriteria,sortDto);
    }
}