package common;

import java.util.ArrayList;
import java.util.List;

import action.controller.ActionController;
import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import common.factory.BeanFactory;
import item.model.domain.InventoryItemDto;
import item.model.domain.ItemInfoDto;
import item.model.domain.ItemUseDto;
import quiz.controller.QuizController;
import quiz.model.domain.QuizConfirmResponseDto;
import quiz.model.domain.QuizDto;
import quiz.model.domain.QuizResponseDto;
import quiz.model.domain.QuizType;
import shop.model.domain.ShopItemFilterDto;
import state.controller.StateController;
import state.model.domain.StateResponseDto;
import state.model.domain.StateUpdateInfoDto;
import state.model.domain.StateUpdateRequestDto;

public class FrontController {
    private static FrontController instance = new FrontController();
    private BeanFactory factory;
    private FrontController() {
        factory = BeanFactory.getInstance();
    }
    public static FrontController getInstance() {
        return instance;
    }
    // 각 메서드는 일종의 인터페이스가 된다.
    // 급할시에는 상관없으나 충돌을 막기위해 임의의 메서드를 막 추가하는 일은 피한다.
    // 가장 기본적인 규칙은 가져오는 경우 get[해당도메인명]
    
        public List<StateResponseDto> getPlayerList() {
        StateController stateController = (StateController) factory.getCtrl("state");
        return stateController.getPlayerList();
    }

    public StateResponseDto createPlayer(String playerName) {
        StateController stateController = (StateController) factory.getCtrl("state");
        return stateController.createPlayer(playerName);
    }

    public StateResponseDto getState(int playerId) {
        StateResponseDto dummy = StateResponseDto.builder()
            .playerId(playerId)
            .hp(100.0)
            .mp(50.0)
            .maxHp(100.0)
            .maxMp(50.0)
            .intelligence(10.0)
            .strength(15.0)
            .money(200.0)
            .build();
        StateController stateController = (StateController) factory.getCtrl("state");
        // return stateController.getState(playerId);
        return dummy;
    }
    
    public StateUpdateInfoDto updateState(StateUpdateRequestDto request) {
        StateController stateController = (StateController) factory.getCtrl("state");
        return stateController.updateState(request);
    }
    
    public ActionResponseDto doAction(int playerId, ActionType actionType) {

        // StateUpdateInfoDto stateChange = StateUpdateInfoDto.builder()
        //     .hp(-10)
        //     .mp(-5)
        //     .money(0)
        //     .intelligence(2)
        //     .strength(3)
        //     .build();
        ActionController actionController = (ActionController) factory.getCtrl("action");
        ActionResponseDto actionResult = actionController.doAction(playerId, actionType);
        if (actionResult.getResult().equals("성공")) {
            actionResult.setResult("Action Successful");
        }
        return actionResult;
    }
    
    public int getTime(int playerId){
        
        return 4;
    }
    
    public List<ItemInfoDto> getShopItemList(int playerId){
        return null;
    }
    
    public void updateTime(int playerId){
    }
    
    public List<ItemInfoDto> getShopOrderList(int playerId, ShopItemFilterDto filterCriteria) {
    
    List<ItemInfoDto> shopItems = new ArrayList<>();


    return shopItems;
    }
    
    public List<ItemInfoDto> getShopFilterList(int playerId){
        return null;
    }
    
    public String buyItem(int playerId, int itemId){
        return "구매실패";
    }
    
    public ItemUseDto useItem(int playerId, int invenId){
        StateUpdateInfoDto stateChange = StateUpdateInfoDto.builder()
            .hp(-10)
            .mp(-5)
            .money(0)
            .intelligence(2)
            .strength(3)
            .build();
        return ItemUseDto.builder()
        .itemName("빵")
        .result("성공")
        .statChange(stateChange)
        .build();
    }
    
    public List<InventoryItemDto> getInventoryInfo(int playerId){
        List<InventoryItemDto> list = new ArrayList<>();
        list.add(InventoryItemDto.builder()
        .count(1)
        .itemId(1)
        .itemName("빵")
        .build());
        return list;
    }
    public List<QuizDto> getQuizInfoList(int playerId){
        
        return List.of();
    }
    public List<QuizDto> getQuizInfoList(int playerId,int level){
        
        return List.of();
    }
    public List<QuizDto> getQuizInfoList(int level, QuizType type){
        QuizController quizController = (QuizController) factory.getCtrl("quiz");
        return quizController.getQuizListByDifficulty(level,type);
    }

    public QuizConfirmResponseDto confirmQuiz(int quizId, String answer) {
        QuizController quizController = (QuizController) factory.getCtrl("quiz");
        return quizController.confirmQuiz(quizId, answer);
    }    

    public QuizResponseDto submitQuizResult(int playerId, int quizLevel, String result){
        QuizController quizController = (QuizController) factory.getCtrl("quiz");
        // StateUpdateInfoDto stateChange = StateUpdateInfoDto.builder()
        //     .hp(0)
        //     .mp(0)
        //     .money(100)
        //     .intelligence(5)
        //     .strength(0)
        //     .build();
        return quizController.submitQuizResult(playerId, quizLevel, result);
    }
   
}