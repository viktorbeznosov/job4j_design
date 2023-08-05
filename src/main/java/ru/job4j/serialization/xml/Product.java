package ru.job4j.serialization.xml;

import ru.job4j.serialization.json.Person;

import java.util.Arrays;

public class Product {
    private final String name;
    private final float price;
    private final boolean available;
    private final String[] categories;
    private final ru.job4j.serialization.json.Person salesManager;

    public Product(String name, float price, boolean available, String[] categories, Person salesManager) {
        this.name = name;
        this.price = price;
        this.available = available;
        this.categories = categories;
        this.salesManager = salesManager;
    }

    @Override
    public String toString() {
        return "Product{"
                + "name='" + name + '\''
                + ", price=" + price
                + ", available=" + available
                + ", categories=" + Arrays.toString(categories)
                + ", salesManager=" + salesManager
                + '}';
    }
}
