package shop.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ShopItemSortDto {
    private String sortBy; // e.g., "name", "category", "cost", etc.
    private boolean ascending; // true for ascending, false for descending
}
