package item.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ItemInfoDto {
    private int id;
    private String name;
    private ItemCategory category;
    private ItemType type;
    private String description;
    private double cost;
}
