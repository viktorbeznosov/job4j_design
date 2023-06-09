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
        int index = getIndexByKey(key);
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

    private int getIndexByKey(K key) {
        int hash = Objects.hashCode(key);
        return indexFor(hash);
    }

    private int hash(int hashCode) {
        return (hashCode >>> 16) ^ hashCode;
    }

    private int indexFor(int hash) {
        return hash % capacity;
    }

    private void expand() {
        capacity *= 2;
        MapEntry<K, V>[] newTable = new MapEntry[capacity];
        for (MapEntry<K, V> entry: table) {
            if (entry != null) {
                int index = getIndexByKey(entry.key);
                newTable[index] = entry;
            }
        }
        table = newTable;
    }

    private boolean checkKey(K key, int index) {
        return table[index] != null
                && (key != null && Objects.hashCode(key) == Objects.hashCode(table[index].key) && key.equals(table[index].key)
                || key == null  && table[index].key == null);
    }

    @Override
    public V get(K key) {
        int index = getIndexByKey(key);
        return checkKey(key, index) ? table[index].value : null;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        int index = getIndexByKey(key);
        if (checkKey(key, index)) {
            table[index] = null;
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
        map.put(6, "6");
        map.put(0, "0000");
        map.put(7, "7");

        System.out.println(map.get(7));
        System.out.println(map.get(null));
        System.out.println(map.get(0));
    }
}