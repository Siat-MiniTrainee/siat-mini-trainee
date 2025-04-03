package action.model.domain;

import lombok.Getter;
import lombok.ToString;
import state.model.domain.StateUpdateInfoDto;

@Getter
@ToString
public class ActionInfoDto {
    private String actionName;
    private StateUpdateInfoDto stageChange;
}
