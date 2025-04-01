package shop.service;

public class ShopService {
    private static volatile ShopService instance = new ShopService();

    private ShopService() {}

    public static ShopService getInstance() {
        return instance;
    }
}
