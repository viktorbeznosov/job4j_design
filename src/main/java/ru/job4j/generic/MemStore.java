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
        if (storage.get(id) == null) {
            return false;
        }
        storage.put(id, model);
        return true;
    }

    @Override
    public boolean delete(String id) {
        if (storage.get(id) == null) {
            return false;
        }
        storage.remove(id);
        return true;
    }

    @Override
    public T findById(String id) {
        return storage.get(id);
    }
}