package state.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StateUpdateRequestDto {
    private int playerId;
    private int hp;
    private int mp;
    private int intelligence;
    private int strength;
}
