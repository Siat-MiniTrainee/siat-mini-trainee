package shop.controller;

import shop.service.ShopService;
import item.model.domain.ItemInfoDto;

import java.util.List;

public class ShopController {
    private ShopService shopService;

    public ShopController() {
        shopService = ShopService.getInstance();
    }

    public void sortItems(String sortOption) {
        List<ItemInfoDto> items = shopService.getSortedItems(sortOption);
        // 여기에 아이템 목록을 받아 정렬된 결과를 출력하거나 다른 로직으로 전달할 수 있습니다.
    }

    public static void main(String[] args) {
        ShopController controller = new ShopController();
        // 여기에 실행 관련 로직을 추가하거나 테스트코드에서 sortItems 메소드를 직접 호출할 수 있습니다.
    }
}