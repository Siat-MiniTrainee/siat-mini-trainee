package shop.model.domain;

import item.model.domain.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShopItemFilterDto {
    private String itemName;
    private ItemCategory itemCategory;
}