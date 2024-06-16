package ru.job4j.ood.dip.examples;

import java.util.LinkedList;
import java.util.List;

public class OrderStore {

    List<Order> orders = new LinkedList<>();

    public boolean save(Order order) {
        return orders.add(order);
    }

}
