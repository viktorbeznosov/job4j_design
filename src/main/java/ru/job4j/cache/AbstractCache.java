package ru.job4j.cache;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCache<K, V> {

    private final Map<K, SoftReference<V>> cache = new HashMap<>();

    public final void put(K key, V value) {
        cache.put(key, new SoftReference<>(value));
    }

    public final V get(K key) {
        V result = cache.get(key) != null ? cache.get(key).get() : null;
        if (result == null) {
            V load = load(key);
            if (load != null) {
                put(key, load);
                result = cache.get(key).get();
            }
        }
        return result;
    }

    protected abstract V load(K key);
}