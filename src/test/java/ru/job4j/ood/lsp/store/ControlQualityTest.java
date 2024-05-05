package ru.job4j.ood.lsp.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.store.foodmap.PercentInterval;
import ru.job4j.ood.lsp.store.foodmap.StoreDistributor;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ControlQualityTest {

    private Map<PercentInterval, StoreDistributor> storeDistributorMap = new HashMap<>();

    private ControlQuality controlQuality;

    @BeforeEach
    public void setup() {
        storeDistributorMap.put(new PercentInterval(0, 25), new StoreDistributor(new Warehouse()));
        storeDistributorMap.put(new PercentInterval(25, 75), new StoreDistributor(new Shop()));
        storeDistributorMap.put(new PercentInterval(75, 100), new StoreDistributor(new Shop(), 20));
        storeDistributorMap.put(new PercentInterval(100, 100), new StoreDistributor(new Trash()));

        this.controlQuality = new ControlQuality(storeDistributorMap);
    }

    @Test
    public void whenExpiredThenMoveToTrash() {
        LocalDate createdDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate expiredDate = LocalDate.of(2024, Month.MAY, 4);
        Food apple = new Food("apple", createdDate, expiredDate, 100);

        controlQuality.distributeFood(apple);
        Store trash = storeDistributorMap.get(new PercentInterval(100, 100)).getStore();

        assertThat(trash.getProducts()).contains(apple);
    }

    @Test
    public void whenExpirationPercentMoreThan75ThenDiscount20() {
        LocalDate createdDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate expiredDate = LocalDate.of(2024, Month.MAY, 15);
        Food orange = new Food("orange", createdDate, expiredDate, 200);

        controlQuality.distributeFood(orange);

        assertThat(orange.getDiscount()).isEqualTo(20);
    }

    @Test
    public void whenExpirationPercentMoreThan75ThenMoveToShop() {
        LocalDate createdDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate expiredDate = LocalDate.of(2024, Month.MAY, 15);
        Food orange = new Food("orange", createdDate, expiredDate, 200);

        controlQuality.distributeFood(orange);
        Store shop = storeDistributorMap.get(new PercentInterval(75, 100)).getStore();

        assertThat(shop.getProducts()).contains(orange);
    }

    @Test
    public void whenExpirationPercentBetween25And75ThenDiscountIs0() {
        LocalDate createdDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate expiredDate = LocalDate.of(2024, Month.JUNE, 10);
        Food sausage = new Food("sausage", createdDate, expiredDate, 150);

        controlQuality.distributeFood(sausage);

        assertThat(sausage.getDiscount()).isEqualTo(0);
    }

    @Test
    public void whenExpirationPercentBetween25And75ThenMoveToShop() {
        LocalDate createdDate = LocalDate.of(2024, Month.APRIL, 1);
        LocalDate expiredDate = LocalDate.of(2024, Month.JUNE, 10);
        Food sausage = new Food("sausage", createdDate, expiredDate, 150);

        controlQuality.distributeFood(sausage);
        Store shop = storeDistributorMap.get(new PercentInterval(25, 75)).getStore();

        assertThat(shop.getProducts()).contains(sausage);
    }

    @Test
    public void whenExpirationPercentBetween0And25ThenMoveToWarehouse() {
        LocalDate createdDate = LocalDate.of(2024, Month.MAY, 4);
        LocalDate expiredDate = LocalDate.of(2024, Month.JUNE, 10);
        Food cheese = new Food("cheese", createdDate, expiredDate, 300);

        controlQuality.distributeFood(cheese);
        Store warehouse = storeDistributorMap.get(new PercentInterval(0, 25)).getStore();

        assertThat(warehouse.getProducts()).contains(cheese);
    }

}