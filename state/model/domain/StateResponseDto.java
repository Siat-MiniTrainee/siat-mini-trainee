package state.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class StateResponseDto {
    private double playerId;
    private double hp;
    private double mp;
    private double maxHp;
    private double maxMp;
    private double intelligence;
    private double strength;
    private double money;
}
