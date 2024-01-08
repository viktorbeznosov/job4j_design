package ru.job4j.cache;

import ru.job4j.cache.menu.Input;
import ru.job4j.cache.menu.Output;
import ru.job4j.cache.menu.UserAction;

public interface CacheFactory<V, K> {
    public K create(V data);

    public UserAction[] actions(Output out, Input in);
}
