package item.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class InventoryItemDto {
    private String invenId;
    private int count;
    private int itemId;
    private String itemName;
}
