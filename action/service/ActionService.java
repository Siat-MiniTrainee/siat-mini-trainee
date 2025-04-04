package action.service;

import java.util.Optional;

import action.model.dao.ActionDao;
import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateInfoDto;
import state.model.domain.StateUpdateRequestDto;
import state.service.StateService;

public class ActionService {
    private static volatile ActionService instance = new ActionService();
    private StateService stateService;
    private ActionDao actionDao;

    private ActionService(){
        this.actionDao = ActionDao.getInstance();
        this.stateService = StateService.getInstance();
    }

    public static ActionService getInstance() {
        return instance;
    }

    public ActionResponseDto doAction(int playerId, ActionType actionType) {
        // actionDao에서 해당 action(문자열 from action where action_name = actionType)
        // 인 액션을 찾고 action_stat_change와 stat_change 테이블을 조인하여 해당 액션의 스텟변경 수치를 찾는다
        // 변경수치를 StateUpdateReqestDto에 담는다. State서비스의 updateState(playerId, updateDto) 함수를 호출한다(구현예정)
        // 서비스를 호출해서 변경에 성공하면 ActionResponseDto에 변경된 수치 정보를 StateUpdateInfoDto에 담고 결과 정보를 담아서 리턴한다.

        Optional<StateResponseDto> playerStateResult = stateService.getState(playerId);
        if (playerStateResult.isPresent()) {
            StateResponseDto playerState = playerStateResult.get();
            StateUpdateInfoDto updateInfo = actionDao.selectActionStatChanges(actionType);
            switch (actionType) {
                case EXERCISE:
                case STUDY:
                case REST:
                    return commonAction(playerId, actionType, playerState, updateInfo);
                case PART_TIME_JOB:
                    if (playerState.getStrength() >= 10) {
                        updateInfo.setMoney(updateInfo.getMoney()+updateInfo.getMoney()*playerState.getStrength()*0.2);
                    }
                    return commonAction(playerId, actionType, playerState, updateInfo);
                default:
                    break;
            }
        }
        
        //actionDao.
        return ActionResponseDto.builder()
        .result("실패")
        .actionName(actionType.name())
        .build();
    }

    private ActionResponseDto commonAction(int playerId, ActionType actionType, StateResponseDto playerState,
            StateUpdateInfoDto updateInfo) {
        if (playerState.getHp() + updateInfo.getHp() <= 0) {
            return ActionResponseDto.builder()
                    .result("HP가 부족합니다.")
                    .actionName(actionType.name())
                    .stageChange(updateInfo)
                    .build();
        } else if (playerState.getMp() + updateInfo.getMp() <= 0) {
            return ActionResponseDto.builder()
                    .result("MP가 부족합니다.")
                    .actionName(actionType.name())
                    .stageChange(updateInfo)
                    .build();
        } else if (playerState.getMoney() + updateInfo.getMoney() < 0) {
            return ActionResponseDto.builder()
                    .result("돈이 부족합니다.")
                    .actionName(actionType.name())
                    .stageChange(updateInfo)
                    .build();
        }
        Optional<StateUpdateInfoDto> stateUpdateResult= stateService.updateState(StateUpdateRequestDto.builder()
                .playerId(playerId)
                .hp(updateInfo.getHp())
                .mp(updateInfo.getMp())
                .intelligence(updateInfo.getIntelligence())
                .strength(updateInfo.getStrength())
                .money(updateInfo.getMoney())
                .build());
        if (stateUpdateResult.isPresent()) {
            return ActionResponseDto.builder()
            .result("성공")
                    .actionName(actionType.name())
                    .stageChange(updateInfo)
            .build();
        }
        return ActionResponseDto.builder()
        .result("실패")
        .actionName(actionType.name())
        .build();

    }
 
     
    //  public void QuizAction() {
    //      try {

    //      } catch (SQLException e) {
    //          e.printStackTrace();
    //      }
    //  }


}
