package common;

import java.util.ArrayList;
import java.util.List;

import action.model.domain.ActionResponseDto;
import action.model.domain.ActionType;
import common.factory.BeanFactory;
import item.model.domain.InventoryItemDto;
import item.model.domain.ItemInfoDto;
import item.model.domain.ItemUseDto;
import quiz.model.domain.QuizConfirmResponseDto;
import quiz.model.domain.QuizDto;
import quiz.model.domain.QuizResponseDto;
import quiz.model.domain.QuizType;
import shop.model.domain.ShopItemFilterDto;
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
        return dummy;
    }
    
    public int updateState(StateUpdateRequestDto request){
        return 0;
    }
    
    public ActionResponseDto doAction(int playerId, ActionType actionType) {

        StateUpdateInfoDto stateChange = StateUpdateInfoDto.builder()
            .hp(-10)
            .mp(-5)
            .money(0)
            .intelligence(2)
            .strength(3)
            .build();

        return ActionResponseDto.builder()
            .result("Action Successful")
            .stageChange(stateChange)
            .build();
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
        
        return null;
    }
    public List<QuizDto> getQuizInfoList(int playerId,int level){
        
        return null;
    }
    public List<QuizDto> getQuizInfoList(int level, QuizType type){
        
        return null;
    }

    public QuizConfirmResponseDto confirmQuiz(int quizId, String answer) {

        return QuizConfirmResponseDto.builder()
        .result("정답")
        .explanation("문제는 이러이러함")
        .answer("정답은 이거임")
        .quizType(QuizType.JAVA)
        .level(1)
        .build();
    }    
    public QuizResponseDto submitQuizResult(int playerId, int quizLevel){
        StateUpdateInfoDto stateChange = StateUpdateInfoDto.builder()
            .hp(0)
            .mp(0)
            .money(100)
            .intelligence(5)
            .strength(0)
            .build();
        return QuizResponseDto.builder()
        .result("퀴즈성공")
        .stateChange(stateChange)
        .build();
    }
   
}