package time.controller;

import time.service.TimeService;

public class TimeController {
    private TimeService timeService;

    public TimeController() {
        this.timeService = TimeService.getInstance(); // Singleton instance
    }

    public int getTime(int playerId) {
        return timeService.getPlayerTime(playerId);
    }

    public int updateTime(int playerId) {
        return timeService.updatePlayerTime(playerId);
    }
}