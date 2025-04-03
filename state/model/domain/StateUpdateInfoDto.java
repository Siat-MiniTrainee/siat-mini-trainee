
package state.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class StateUpdateInfoDto {
    private double hp;
    private double mp;
    private double money;
    private double intelligence;
    private double strength;
}
