package ru.job4j.map;

import java.util.*;

public class SimpleMap<K, V> implements Map<K, V> {

    private static final float LOAD_FACTOR = 0.75f;

    private int capacity = 8;

    private int count = 0;

    private int modCount = 0;

    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        boolean result = false;
        int hash = key != null ? hash(key.hashCode()) : 0;
        int index = indexFor(hash);
        if (table[index] == null) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
            result = true;
            if ((float) count / capacity >= LOAD_FACTOR) {
                expand();
            }
        }

        return result;
    }

    private int hash(int hashCode) {
        return (hashCode >>> capacity) ^ hashCode;
    }

    private int indexFor(int hash) {
        return hash % capacity;
    }

    private void expand() {
        capacity *= 2;
        table = Arrays.copyOf(table, capacity);
    }

    @Override
    public V get(K key) {
        V result = null;

        for (MapEntry<K, V> entry: table) {
            if (entry != null && entry.key == key) {
                result = entry.value;
                break;
            }
        }

        return result;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        if (get(key) != null) {
            int hash = key != null ? hash(key.hashCode()) : 0;
            int index = indexFor(hash);
            table[index] = null;
            count--;
            modCount++;
            result = true;
        }
        return result;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            private int index = 0;

            private int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (modCount > expectedModCount) {
                    throw new ConcurrentModificationException();
                }
                while (index < capacity && table[index] == null) {
                    index++;
                }
                return index < capacity;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[index++].key;
            }
        };
    }

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        Map<Integer, String> map = new SimpleMap<>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(null, "0000");
        map.put(5, "5");
    }
}