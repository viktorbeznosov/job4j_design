package ru.job4j.ood.lsp.store;

public class Warehouse extends AbstractStore {

    @Override
    public void add(Food food) {
        food.setDiscount(0);
        super.add(food);
    }

}
