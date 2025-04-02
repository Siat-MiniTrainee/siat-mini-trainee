package shop.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ShopItemDto {
    private int itemId;
    private String itemName;
    private double cost;
}
