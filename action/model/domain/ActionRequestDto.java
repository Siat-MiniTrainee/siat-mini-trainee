package action.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActionRequestDto {
    private int playerId;
    private int actionId;
}
