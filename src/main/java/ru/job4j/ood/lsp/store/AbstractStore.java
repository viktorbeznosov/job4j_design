package ru.job4j.ood.lsp.store;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractStore implements Store {
    protected List<Food> products = new LinkedList<>();

    public void add(Food food) {
        products.add(food);
    }

    public void delete(Food food) {
        for (Food item: products) {
            if (food.equals(item)) {
                products.remove(products.indexOf(item));
            }
        }
    }

    public List<Food> getProducts() {
        return products;
    }
}
