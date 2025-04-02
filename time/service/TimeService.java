package time.service;

public class TimeService {
    private static volatile TimeService instance = new TimeService();

    private TimeService() {}

    public static TimeService getInstance() {
        return instance;
    }
}
