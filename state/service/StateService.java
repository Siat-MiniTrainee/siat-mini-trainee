package state.service;

public class StateService {
    private static volatile StateService instance = new StateService();

    private StateService() {}

    public static StateService getInstance() {
        return instance;
    }
}
