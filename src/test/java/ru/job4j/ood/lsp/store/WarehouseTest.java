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

class WarehouseTest {
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
        this.controlQuality = new ControlQuality(storeDistributorMap);
    }

    @Test
    public void whenExpirationPercentBetween0And25ThenMoveToWarehouse() {
        LocalDate createdDate = LocalDate.of(year, month, date - 4);
        LocalDate expiredDate = LocalDate.of(year, month + 1, date);
        Food cheese = new Food("cheese", createdDate, expiredDate, 300);

        controlQuality.distributeFood(cheese);
        Store warehouse = storeDistributorMap.get(new PercentInterval(0, 25)).getStore();

        assertThat(warehouse.getProducts()).contains(cheese);
    }
}