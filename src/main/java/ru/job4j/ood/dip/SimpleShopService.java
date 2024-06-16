package ru.job4j.ood.dip;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleShopService {

    private static final Logger LOGGER = Logger.getLogger("Shop logger");

    private final ShopStore shopStore;

    private final OrderStore orderStore;

    public SimpleShopService(ShopStore shopStore, OrderStore orderStore) {
        this.shopStore = shopStore;
        this.orderStore = orderStore;
    }

    public boolean createBucket(User user) {
        return shopStore.saveUser(user);
    }

    public boolean createOrder(User user, Order order) {
        return shopStore.saveOrder(user, order);
    }

    private Optional<Order> findOrder(User user, Order order) {
        Set<Order> orders = shopStore.getOrders(user);
        return orders.stream()
                .filter(o -> o.getId() == order.getId())
                .findFirst();
    }

    public boolean removeOrder(User user, Order order) {
        Set<Order> orders = shopStore.getOrders(user);
        if (orders == null) {
            return false;
        }
        return orders.remove(order);
    }

    public boolean addProduct(User user, Order order, Product product) {
        Optional<Order> orderDB = findOrder(user, order);
        if (orderDB.isEmpty()) {
            return false;
        }
        return orderStore.add(orderDB.get(), product);
    }

    public boolean removeProduct(User user, Order order, Product product) {
        Optional<Order> orderDB = findOrder(user, order);
        if (orderDB.isEmpty()) {
            return false;
        }
        return orderStore.remove(orderDB.get(), product.id);
    }

    public Check payOrder(User user, Order order) {
        Optional<Order> orderDB = findOrder(user, order);
        if (orderDB.isEmpty()) {
            LOGGER.log(Level.WARNING, "Get error with " + user + " " + order);
            throw new IllegalArgumentException("Invalid order");
        }
        if (orderDB.get().isPayed()) {
            LOGGER.log(Level.WARNING, "Get error with "  + user + " " + order);
            throw new IllegalArgumentException("Already payed!");
        }
        orderDB.get().setPayed(true);
        return new Check((int) (System.currentTimeMillis() % 1000_000), "Payed: " + orderDB.get().getId());
    }

}