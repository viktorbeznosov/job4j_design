package ru.job4j.set;

import ru.job4j.collection.SimpleArrayList;

import java.util.Iterator;

public class SimpleSet<T> implements Set<T> {
    private SimpleArrayList<T> set = new SimpleArrayList<>(0);

    @Override
    public boolean add(T value) {
        boolean result = false;
        if (!contains(value)) {
            set.add(value);
            result = true;
        }

        return result;
    }

    @Override
    public boolean contains(T value) {
        boolean result = false;
        Iterator<T> iterator = iterator();
        T next = null;
        while (iterator.hasNext() && !result) {
            next = iterator.next();
            result = next == null ? next == value : next.equals(value);
        }

        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return set.iterator();
    }

    public static void main(String[] args) {
        Set<Integer> set = new SimpleSet<>();
        set.add(1);
        set.add(2);
        set.add(1);
        set.add(3);
        set.add(null);
        set.add(null);
        set.add(2);
        set.add(null);
        set.add(5);
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
