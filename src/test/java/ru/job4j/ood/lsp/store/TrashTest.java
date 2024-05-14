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

class TrashTest {
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

        storeDistributorMap.put(new PercentInterval(100, 100), new StoreDistributor(new Shop()));
        this.controlQuality = new ControlQuality(storeDistributorMap);
    }

    @Test
    public void whenExpiredThenMoveToTrash() {
        LocalDate createdDate = LocalDate.of(year, month - 1, 1);
        LocalDate expiredDate = LocalDate.of(year, month, 4);
        Food apple = new Food("apple", createdDate, expiredDate, 100);

        controlQuality.distributeFood(apple);
        Store trash = storeDistributorMap.get(new PercentInterval(100, 100)).getStore();

        assertThat(trash.getProducts()).contains(apple);
    }
}