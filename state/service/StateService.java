package state.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import item.model.domain.ItemInfoDto;
import item.service.ItemService;
import state.model.dao.StateDao;
import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateInfoDto;
import state.model.domain.StateUpdateRequestDto;

public class StateService {
    private static volatile StateService instance = new StateService();
    private StateDao stateDao;
    private ItemService itemService;

    private StateService() {
        stateDao=StateDao.getInstance();
        itemService=ItemService.getInstance();
    }

    public static StateService getInstance() {
        return instance;
    }
    public List<StateResponseDto> getPlayerList(){
       
        return  stateDao.selectPlayerAllState();
    }
    public Optional<StateResponseDto> createPlayer(String playerName) {
        
        StateResponseDto newPlayer = StateResponseDto.builder()
                .playerName(playerName)
                .maxHp(100) 
                .maxMp(100)
                .hp(100) 
                .mp(100)
                .intelligence(0)
                .strength(0)
                .money(0)
                .build();
        
        // 데이터베이스에 새 플레이어 기록을 삽입
        int playerId = stateDao.insertPlayer(newPlayer);
        
        if(playerId!=-1){
            newPlayer.setPlayerId(playerId);
            
            int playerDetailResult = stateDao.insertPlayerDetail(newPlayer);
            if(playerDetailResult>0){
                return Optional.of(newPlayer);
            }
        }
        return Optional.empty();
    }

    public Optional<StateResponseDto> getState(int playerId) {
        return stateDao.selectPlayerOneState(playerId);
    }

    public Optional<StateUpdateInfoDto> updateState(StateUpdateRequestDto updateRequestDto) {
        Optional<StateResponseDto> stateInfo = stateDao.selectPlayerOneState(updateRequestDto.getPlayerId());
        if(stateInfo.isPresent()){
            StateResponseDto state =stateInfo.get();
            StateUpdateInfoDto updateInfo = applyInventoryItem(updateRequestDto);
            // 업데이트시 소지품을 검사해서 패시브 효과를 로직이 필요. 아직 아이템이 미완이라 보류
            StateUpdateRequestDto updatedState = StateUpdateRequestDto.builder()
                    .playerId(updateRequestDto.getPlayerId())
                    .hp(state.getHp() + updateInfo.getHp())
                    .mp(state.getMp() + updateInfo.getMp())
                    .intelligence(state.getIntelligence() + updateInfo.getIntelligence())
                    .strength(state.getStrength() + updateRequestDto.getStrength())
                    .money(state.getMoney() + updateRequestDto.getMoney())
                    .build();
            int result =stateDao.updatePlayerState(updatedState);
            
            if(result>0){
                return Optional.of(updateInfo);
                
            }
            
        };
        return Optional.empty();
    }
    public StateUpdateInfoDto applyInventoryItem(StateUpdateRequestDto updateRequestDto){
        List<ItemInfoDto> passiveItemList = new ArrayList<>();
        return StateUpdateInfoDto.builder().build();
    }
}
