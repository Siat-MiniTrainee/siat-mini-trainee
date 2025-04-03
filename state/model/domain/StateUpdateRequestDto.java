package state.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StateUpdateRequestDto {
    private int playerId;
    private double hp;
    private double mp;
    private double maxHp;
    private double maxMp;
    private double intelligence;
    private double strength;
    private double money;
}
