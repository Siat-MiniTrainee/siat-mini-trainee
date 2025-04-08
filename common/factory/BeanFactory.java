package common.factory;

import java.util.HashMap;
import java.util.Map;

import action.controller.ActionController;
import item.controller.ItemController;
import quiz.controller.QuizController;
import shop.controller.ShopController;
import state.controller.StateController;
import time.controller.TimeController;

public class BeanFactory {
    private static volatile BeanFactory instance=new BeanFactory();
    public Map<String, Object> map;

    private BeanFactory() {
        map = new HashMap<>();
        map.put("action", new ActionController());
        map.put("item",new ItemController());
        map.put("shop",new ShopController());
        map.put("state",new StateController());
        map.put("time",new TimeController());
        map.put("quiz", new QuizController());
    }
    public static BeanFactory getInstance() {
        return instance;
    }
    public Object getCtrl(String key) {
        return map.get(key);
    }
}