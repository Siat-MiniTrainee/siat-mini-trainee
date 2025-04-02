package state.controller;

import state.model.domain.StateDto;
import state.service.StateService;

public class StateController {
    private StateService stateService = StateService.getInstance();

    public StateDto getState() {
        return stateService.getState();
    }

    public void updateState(StateDto updatedState) {
        stateService.updateState(updatedState);
    }
}