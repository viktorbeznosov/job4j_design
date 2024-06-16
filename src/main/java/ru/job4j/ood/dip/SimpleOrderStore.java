package ru.job4j.ood.dip;

import java.util.HashMap;
import java.util.Map;

public class SimpleOrderStore implements OrderStore {
    private Map<Order, Map<Integer, Product>> orderStore = new HashMap<>();

    @Override
    public boolean add(Order order, Product product) {
        if (!orderStore.get(order).isEmpty() && !orderStore.get(order).containsKey(product.getId())) {
            return orderStore.get(order).put(product.getId(), product) != null;
        } else if (orderStore.get(order).isEmpty()) {
            return orderStore.put(order, Map.of(product.getId(), product)) != null;
        }

        return false;
    }

    @Override
    public boolean remove(Order order, int id) {
        if (!orderStore.isEmpty() && !orderStore.get(order).isEmpty()) {
            return orderStore.get(order).remove(id) != null;
        }
        return false;
    }

    @Override
    public void clear(Order order) {
        if (!orderStore.isEmpty() && !orderStore.get(order).isEmpty()) {
            orderStore.get(order).clear();
        }
    }
}
