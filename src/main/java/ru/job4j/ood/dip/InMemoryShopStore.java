package ru.job4j.ood.dip;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InMemoryShopStore implements ShopStore {
    private HashMap<User, Set<Order>> serviceDB = new HashMap<>();

    @Override
    public boolean saveUser(User user) {
        if (serviceDB.containsKey(user)) {
            return false;
        }

        return serviceDB.put(user, new HashSet<>()) != null;
    }

    @Override
    public boolean saveOrder(User user, Order order) {
        Set<Order> orders = serviceDB.getOrDefault(user, Set.of());
        if (orders.isEmpty()) {
            return false;
        }
        return orders.add(order);
    }

    @Override
    public Set<Order> getOrders(User user) {
        return serviceDB.get(user);
    }

    @Override
    public Set<User> getUsers() {
        return serviceDB.keySet();
    }
}
