package common;

import java.util.List;

import action.model.domain.ActionResponseDto;
import common.factory.BeanFactory;
import item.model.domain.InventoryItemDto;
import item.model.domain.ItemUseDto;
import quiz.model.domain.QuizDto;
import shop.model.domain.ShopItemDto;
import state.model.domain.StateResponseDto;
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
    public StateResponseDto getState(int playerId){
        
        return null;
    }
    
    public int updateState(StateUpdateRequestDto request){
        return 0;
    }
    
    public ActionResponseDto doAction(int playerId){
        
        return null;
    }
    
    public int getTime(int playerId){
        
        return 0;
    }
    
    public List<ShopItemDto> getShopItemList(int playerId){
        return null;
    }
    
    public void updateTime(int playerId){
    }
    
    public List<ShopItemDto> getShopOrderList(int playerId){
        return null;
    }
    
    public List<ShopItemDto> getShopFilterList(int playerId){
        return null;
    }
    
    public String buyItem(int playerId, int itemId){
        return "구매실패";
    }
    
    public ItemUseDto useItem(int playerId, int invenId){
        return null;
    }
    
    public List<InventoryItemDto> getInventoryInfo(int playerId){
        
        return null;
    }
    
    public List<QuizDto> getQuizInfo(int playerId){
        
        return null;
    }
    
    public int confirmQuiz(int playerId, int quizId, String answer){
        return 0;
    }
    
    public int submitQuizResult(int playerId, List<Integer> quizs, List<String> answers){
        return 0;
    }
   
}