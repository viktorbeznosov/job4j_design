package ru.job4j.ood.dip;

public interface OrderStore {
    public boolean add(Order order, Product product);

    public boolean remove(Order order, int id);

    public void clear(Order order);
}
