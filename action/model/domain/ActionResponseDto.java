package action.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import state.model.domain.StateUpdateInfoDto;

@Getter
@Setter
@Builder
@ToString
public class ActionResponseDto {
    private String result;
    private String actionName;
    private StateUpdateInfoDto stageChange;
}
