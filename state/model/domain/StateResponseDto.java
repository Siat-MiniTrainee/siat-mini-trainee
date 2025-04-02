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
    private int playerId;
    private int hp;
    private int mp;
    private int intelligence;
    private int strength;
    private int money;
    private int score;
}
