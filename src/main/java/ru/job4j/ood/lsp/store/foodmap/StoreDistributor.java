package ru.job4j.ood.lsp.store.foodmap;

import ru.job4j.ood.lsp.store.Store;

public class StoreDistributor {

    private Store store;

    public StoreDistributor() {
    }

    public StoreDistributor(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }
}
