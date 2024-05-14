package ru.job4j.ood.lsp.store;

public class Trash extends AbstractStore {

    @Override
    public void add(Food food) {
        food.setPrice(0);
        super.add(food);
    }
}
