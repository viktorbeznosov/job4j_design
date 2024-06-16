package ru.job4j.ood.dip.examples;

public class Order {
    private String name;

    private Integer id;

    public Order(String name, Integer id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }
}
