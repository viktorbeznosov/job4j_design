package ru.job4j.ood.lsp.store;

import java.util.List;

public interface Store {
    public void add(Food food);

    public void delete(Food food);

    public List<Food> getProducts();
}
