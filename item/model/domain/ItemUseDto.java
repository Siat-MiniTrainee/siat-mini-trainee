package item.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.model.domain.StateUpdateInfoDto;

@Getter
@Setter
@ToString
@Builder
public class ItemUseDto {
    private String result;
    private String itemName;
    private int itemId;
    private StateUpdateInfoDto statChange;
}
