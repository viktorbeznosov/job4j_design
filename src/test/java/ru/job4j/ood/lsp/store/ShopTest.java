package ru.job4j.ood.lsp.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.store.foodmap.PercentInterval;
import ru.job4j.ood.lsp.store.foodmap.StoreDistributor;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ShopTest {

    private Map<PercentInterval, StoreDistributor> storeDistributorMap = new HashMap<>();

    private ControlQuality controlQuality;

    private int year;

    private int month;

    private int date;

    @BeforeEach
    public void setup() {
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        date = Calendar.getInstance().get(Calendar.DATE);

        storeDistributorMap.put(new PercentInterval(25, 100), new StoreDistributor(new Shop()));
        this.controlQuality = new ControlQuality(storeDistributorMap);
    }

    @Test
    public void whenExpirationPercentBetween25And100ThenMoveToShop() {
        LocalDate createdDate = LocalDate.of(year, month - 1, 1);
        LocalDate expiredDate = LocalDate.of(year, month + 1, 10);
        Food apple = new Food("apple", createdDate, expiredDate, 100);

        controlQuality.distributeFood(apple);
        Store store = storeDistributorMap.get(new PercentInterval(25, 100)).getStore();
        assertThat(store.getProducts()).contains(apple);
    }

    @Test
    public void whenExpirationPercentBetween25And75ThenDiscountIs0() {
        LocalDate createdDate = LocalDate.of(year, month - 1, 1);
        LocalDate expiredDate = LocalDate.of(year, month + 1, 10);
        Food sausage = new Food("sausage", createdDate, expiredDate, 150);

        controlQuality.distributeFood(sausage);

        assertThat(sausage.getDiscount()).isEqualTo(0);
    }

    @Test
    public void whenExpirationPercentMoreThan75ThenMoveToShop() {
        LocalDate createdDate = LocalDate.of(year, month - 1, 1);
        LocalDate expiredDate = LocalDate.of(year, month, 15);
        Food orange = new Food("orange", createdDate, expiredDate, 200);

        controlQuality.distributeFood(orange);
        Store shop = storeDistributorMap.get(new PercentInterval(25, 100)).getStore();
        assertThat(shop.getProducts()).contains(orange);
    }

    @Test
    public void whenExpirationPercentBetween25And75ThenMoveToShop() {
        LocalDate createdDate = LocalDate.of(year, month - 1, 1);
        LocalDate expiredDate = LocalDate.of(year, month + 1, 10);
        Food sausage = new Food("sausage", createdDate, expiredDate, 150);

        controlQuality.distributeFood(sausage);
        Store shop = storeDistributorMap.get(new PercentInterval(25, 100)).getStore();

        assertThat(shop.getProducts()).contains(sausage);
    }
}