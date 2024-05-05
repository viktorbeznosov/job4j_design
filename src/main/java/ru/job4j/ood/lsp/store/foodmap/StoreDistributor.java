package ru.job4j.ood.lsp.store.foodmap;

import ru.job4j.ood.lsp.store.Store;

public class StoreDistributor {

    private Store store;

    private int discount;

    public StoreDistributor() {
    }

    public StoreDistributor(Store store) {
        this.store = store;
    }

    public StoreDistributor(Store store, int discount) {
        this.store = store;
        this.discount = discount;
    }

    public Store getStore() {
        return store;
    }

    public int getDiscount() {
        return discount;
    }
}
