package ru.job4j.generic;

import java.util.HashMap;
import java.util.Map;

public final class MemStore<T extends Base> implements Store<T> {

    private final Map<String, T> storage = new HashMap<>();

    @Override
    public void add(T model) {
        if (storage.get(model.getId()) == null) {
            storage.put(model.getId(), model);
        }
    }

    @Override
    public boolean replace(String id, T model) {
        boolean rsl = false;
        if (storage.get(id) != null) {
            storage.put(id, model);
            rsl = true;
        }

        return rsl;
    }

    @Override
    public boolean delete(String id) {
        boolean rsl = false;
        if (storage.get(id) != null) {
            storage.remove(id);
            rsl = true;
        }

        return rsl;
    }

    @Override
    public T findById(String id) {
        return storage.get(id);
    }
}