package time.service;

import time.model.dao.TimeDao;

import java.sql.SQLException;

public class TimeService {
    private static volatile TimeService instance = new TimeService();
    private TimeDao timeDao;

    private TimeService() {
        this.timeDao = TimeDao.getInstance();
    }

    public static TimeService getInstance() {
        return instance;
    }

    public int createPlayerTime(int playerId, int initTime) {
        return timeDao.insertPlayerTime(playerId, initTime);
    }

    public int getPlayerTime(int playerId) {
        return timeDao.getPlayerTime(playerId);
    }

    
    public int updatePlayerTime(int playerId) {
        int playerTime = getPlayerTime(playerId);
        if(timeDao.updatePlayerTime(playerId, playerTime + 1)>0){
            return playerTime+1;
        }
        return -1;

    }

    public int updatePlayerTime(int playerId, int newTime) {
        if (timeDao.updatePlayerTime(playerId, newTime) > 0) {
            return timeDao.updatePlayerTime(playerId, newTime);
        }
        return -1;
    }
}