package state.controller;

import java.util.List;

import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateInfoDto;
import state.model.domain.StateUpdateRequestDto;
import state.service.StateService;

public class StateController {
    private StateService stateService = StateService.getInstance();
    public List<StateResponseDto> getPlayerList(){
        return stateService.getPlayerList();
    }
    public StateResponseDto createPlayer(String playerName){
        return stateService.createPlayer(playerName).orElse(StateResponseDto.builder().build());
    }
    public StateResponseDto getState(int playerId) {
        return stateService.getState(playerId).orElse(StateResponseDto.builder().build());
    }

    public StateUpdateInfoDto updateState(StateUpdateRequestDto updatedState) {
        return stateService.updateState(updatedState).orElse(StateUpdateInfoDto.builder().build());
    }
}