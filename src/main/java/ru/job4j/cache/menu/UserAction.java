package ru.job4j.cache.menu;

import ru.job4j.cache.AbstractCache;

public interface UserAction {
    String name();

    boolean execute(AbstractCache cache);
}
