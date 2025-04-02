package state.service;

import state.model.dao.StateDao;
import state.model.domain.StateDto;

public class StateService {
    private static volatile StateService instance = new StateService();
    private StateDao stateDao = StateDao.getInstance();

    private StateService() {}

    public static StateService getInstance() {
        return instance;
    }

    public StateDto getState() {
        return stateDao.getState();
    }

    public void updateState(StateDto updatedState) {
        stateDao.updateState(updatedState);
    }
}