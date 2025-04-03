package action.controller;

import java.sql.SQLException;
import java.util.Scanner;

import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import action.service.ActionService;
import state.model.domain.StateUpdateInfoDto;
import state.service.StateService;

public class ActionController {
    
    private ActionService actionService;

    public ActionController() {
        this.actionService = ActionService.getInstance();
    }

    public ActionResponseDto doAction(int playerId, ActionType actionType) {
        actionService.doAction(playerId, actionType);
        return null;
    }
    
    
}

