package item.service;

public class ItemService {
    private static volatile ItemService instance = new ItemService();

    private ItemService() {}

    public static ItemService getInstance() {
        return instance;
    }
}
