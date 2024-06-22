package ru.job4j.ood.lsp.store;

import ru.job4j.ood.lsp.store.foodmap.PercentInterval;
import ru.job4j.ood.lsp.store.foodmap.StoreDistributor;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class ControlQuality {

    private Map<PercentInterval, StoreDistributor> map;

    public ControlQuality() {
    }

    public ControlQuality(Map<PercentInterval, StoreDistributor> map) {
        this.map = map;
    }

    private boolean intervalCondition(float expirationPercent, PercentInterval interval) {
        return expirationPercent >= interval.getStart() && expirationPercent <= interval.getEnd()
                || interval.getStart() == interval.getEnd() && expirationPercent >= interval.getEnd();
    }

    public void distributeFood(Food food) {
        if (map.keySet().isEmpty()) {
            return;
        }

        try {
            float expirationPercent = food.getExpirationPercent();
            PercentInterval interval = map.keySet()
                    .stream()
                    .filter(key -> intervalCondition(expirationPercent, key))
                    .findFirst()
                    .get();

            Store store = map.get(interval).getStore();
            store.add(food);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resort() {
        List<Food> products = getProducts();
        getStores().forEach(e -> e.getProducts().clear());
        products.forEach(this::distributeFood);
    }

    private List<Food> getProducts() {
        return getStores().stream()
            .map(e -> e.getProducts())
            .flatMap(e -> e.stream())
            .collect(Collectors.toList());
    }

    private List<Store> getStores() {
        return map.entrySet()
            .stream()
            .map(e -> e.getValue().getStore())
            .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        LocalDate now = LocalDate.now();
        LocalDate createdDate = LocalDate.of(2024, Month.MAY, 4);
        LocalDate expiredDate = LocalDate.of(2024, Month.JUNE, 10);

        Map<PercentInterval, StoreDistributor> storeDistributorMap = new HashMap<>();
        storeDistributorMap.put(new PercentInterval(0, 25), new StoreDistributor(new Warehouse()));
        storeDistributorMap.put(new PercentInterval(25, 100), new StoreDistributor(new Shop()));
        storeDistributorMap.put(new PercentInterval(100, 100), new StoreDistributor(new Trash()));

        Food apple = new Food("apple", createdDate, expiredDate, 100);
        Food orange = new Food("orange", createdDate, expiredDate, 200);
        ControlQuality controlQuality = new ControlQuality(storeDistributorMap);
        controlQuality.distributeFood(orange);
    }
}
