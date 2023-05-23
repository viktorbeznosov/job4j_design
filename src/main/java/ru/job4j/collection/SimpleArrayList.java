package ru.job4j.collection;

import java.util.*;

public class SimpleArrayList<T> implements SimpleList<T> {

    private T[] container;
    private int size;
    private int modCount;

    public SimpleArrayList(int capacity) {
        container = (T[]) new Object[capacity];
        size = 0;
        modCount = 0;
    }

    @Override
    public void add(T value) {
        if (size == container.length) {
            grow();
        }
        container[size] = value;
        size++;
        modCount++;
    }

    @Override
    public T set(int index, T newValue) {
        Objects.checkIndex(index, size);
        T changed = container[index];
        container[index] = newValue;
        return changed;
    }

    @Override
    public T remove(int index) {
        Objects.checkIndex(index, size);
        T removed = container[index];

        System.arraycopy(container, index + 1, container, index, container.length - index - 1);
        container[container.length - 1] = null;
        size--;
        modCount++;
        return removed;
    }

    @Override
    public T get(int index) {
        Objects.checkIndex(index, size);
        return container[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount > expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < container.length && container[index] == null) {
                    index++;
                }
                return index < container.length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (modCount > expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                return container[index++];
            }
        };
    }

    private void grow() {
        int newLength = container.length > 0 ? container.length * 2 : 1;
        container = Arrays.copyOf(container, newLength);
    }
}