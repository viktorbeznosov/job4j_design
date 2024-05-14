package ru.job4j.ood.lsp.store;

public class Shop extends AbstractStore {

    @Override
    public void add(Food food) {
        setDiscount(food);
        super.add(food);
    }

    private void setDiscount(Food food) {
        if (food.getExpirationPercent() > 75 && food.getExpirationPercent() < 100) {
            food.setDiscount(20);
        }
    }

}
