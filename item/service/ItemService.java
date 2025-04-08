package item.service;

import item.model.dao.ItemDao;
import state.model.dao.StateDao;
import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateInfoDto;
import state.model.domain.StateUpdateRequestDto;
import state.service.StateService;
import item.model.domain.ItemCategory;
import item.model.domain.ItemInfoDto;
import item.model.domain.ItemType;

public class ItemService {
    private static volatile ItemService instance = new ItemService();
    private ItemDao itemDao = ItemDao.getInstance();
    private StateService stateService = StateService.getInstance();

    private ItemService() {}

    public static ItemService getInstance() {
        return instance;
    }

    public void applyItemToState(int itemId) {
        // 아이템 정보 가져오기
        ItemInfoDto itemInfo = itemDao.getItemInfo(itemId);
        StateResponseDto currentState = stateService.getState();

        // 아이템 능력치를 가져와서 적용
        StateUpdateInfoDto statChanges = calculateStatChanges(itemInfo);
        updateStateWithItemEffects(currentState, statChanges);

        // 상태 업데이트
        stateService.updateState(currentState);
    }

    private StateUpdateInfoDto calculateStatChanges(ItemInfoDto itemInfo) {
        // 아이템의 속성에 따라 상태 변화 계산
        return StateUpdateInfoDto.builder()
            .hp(itemInfo.getType() == ItemType.ACTIVE ? 5 : 0) // 예시로 HP 수치 증가
            .intelligence(itemInfo.getCategory() == ItemCategory.FOOD ? 2 : 0) // 예시로 Intelligence 증가
            .build();
    }
    
    private void updateStateWithItemEffects(StateUpdateRequestDto state, StateUpdateInfoDto changes) {
        // 변화량을 적용하여 상태 업데이트
        state.setHp(state.getHp() + changes.getHp());
        state.setIntelligence(state.getIntelligence() + changes.getIntelligence());
        // y 같은 모든 수정된 속성을 적용
    }
}