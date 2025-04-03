package action.service;

public class ActionService {
    private static volatile ActionService instance = new ActionService();

    private ActionService() {}

    public static ActionService getInstance() {
        return instance;
    }
}
