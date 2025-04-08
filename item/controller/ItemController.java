package item.controller;

import item.service.ItemService;

public class ItemController {
    private ItemService itemService = ItemService.getInstance();

    public void applyItemEffects(int itemId) {
        itemService.applyItemToState(itemId);
    }
}