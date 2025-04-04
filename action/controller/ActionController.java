package action.controller;

import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import action.service.ActionService;

public class ActionController {
    
    private ActionService actionService;

    public ActionController() {
        this.actionService = ActionService.getInstance();
    }

    public ActionResponseDto doAction(int playerId, ActionType actionType) {
        return actionService.doAction(playerId, actionType);
    }
    
    
}

