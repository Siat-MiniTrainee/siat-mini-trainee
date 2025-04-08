package action.model.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import state.model.domain.StateUpdateInfoDto;

@Getter
@ToString
@Builder
public class ActionInfoDto {
    private String actionName;
    private ActionType actionType;
    private StateUpdateInfoDto stageChange;
}
