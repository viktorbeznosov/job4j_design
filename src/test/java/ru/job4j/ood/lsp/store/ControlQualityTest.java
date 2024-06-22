package ru.job4j.ood.lsp.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.ood.lsp.store.foodmap.PercentInterval;
import ru.job4j.ood.lsp.store.foodmap.StoreDistributor;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ControlQualityTest {

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

        storeDistributorMap.put(new PercentInterval(0, 25), new StoreDistributor(new Warehouse()));
        storeDistributorMap.put(new PercentInterval(25, 100), new StoreDistributor(new Shop()));
        storeDistributorMap.put(new PercentInterval(100, 100), new StoreDistributor(new Trash()));

        this.controlQuality = new ControlQuality(storeDistributorMap);
    }

    @Test
    public void storeDistributeTest() {
        LocalDate createdDate = LocalDate.of(year, month, date - 4);
        LocalDate expiredDate = LocalDate.of(year, month + 1, date);
        Food cheese = new Food("cheese", createdDate, expiredDate, 300);

        createdDate = LocalDate.of(year, month - 1, 1);
        expiredDate = LocalDate.of(year, month + 1, 10);
        Food apple = new Food("apple", createdDate, expiredDate, 100);

        createdDate = LocalDate.of(year, month - 1, 1);
        expiredDate = LocalDate.of(year, month, 4);
        Food rancidSausage = new Food("rancidSausage", createdDate, expiredDate, 100);

        controlQuality.distributeFood(cheese);
        controlQuality.distributeFood(apple);
        controlQuality.distributeFood(rancidSausage);

        Store warehouse = storeDistributorMap.get(new PercentInterval(0, 25)).getStore();
        Store shop = storeDistributorMap.get(new PercentInterval(25, 100)).getStore();
        Store trash = storeDistributorMap.get(new PercentInterval(100, 100)).getStore();

        assertThat(warehouse.getProducts()).contains(cheese).doesNotContain(apple).doesNotContain(rancidSausage);
        assertThat(shop.getProducts()).contains(apple).doesNotContain(cheese).doesNotContain(rancidSausage);
        assertThat(trash.getProducts()).contains(rancidSausage).doesNotContain(cheese).doesNotContain(apple);
    }

    @Test
    public void whenResortedTest() throws NoSuchFieldException, IllegalAccessException {
        LocalDate createdDate = LocalDate.of(year, month, date - 4);
        LocalDate expiredDate = LocalDate.of(year, month, date - 1);
        Food cheese = new Food("cheese", createdDate, expiredDate, 300);

        Store shop = storeDistributorMap.get(new PercentInterval(25, 100)).getStore();
        shop.add(cheese);

        controlQuality.resort();
        Store trash = storeDistributorMap.get(new PercentInterval(100, 100)).getStore();
        assertThat(trash.getProducts()).contains(cheese);
        assertThat(shop.getProducts()).doesNotContain(cheese);
    }
}