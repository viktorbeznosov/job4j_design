package ru.job4j.list;

import java.util.*;

public class ListUsage {
    public static void main(String[] args) {
        List<String> rsl = new ArrayList<>();
        rsl.add("one");
        rsl.add("two");
        rsl.add("three");
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("three");

        rsl.retainAll(list);
        for (int i = 0; i < rsl.size(); i++) {
            System.out.println("Текущий элемент: " + rsl.get(i));
        }

        Iterator<String> iterator = rsl.iterator();
        while (iterator.hasNext()) {
            System.out.println("Текущий элемент: " + iterator.next());
        }

        ListIterator<String> listIterator = rsl.listIterator(1);
        while (listIterator.hasNext()) {
            System.out.println("Текущий элемент: " + listIterator.next());
        }

        rsl.removeIf(s -> s.length() <= 3);
        rsl.stream().forEach(System.out::println);

        boolean b = rsl.contains("two");
        System.out.println("Список содержит элемент: " + b);

        int i = rsl.indexOf("two");
        System.out.println("Индекс элемента в списке: " + i);

        rsl.add("one");
        i = rsl.lastIndexOf("one");
        System.out.println("Индекс элемента в списке: " + i);

        rsl.add("one");
        i = rsl.lastIndexOf("one");
        System.out.println("Индекс элемента в списке: " + i);

        List<Integer> rsl2 = List.of(1, 2, 3);
        int size = rsl2.size();
        System.out.println("Размер списка равен: " + size);

        rsl.sort(Comparator.reverseOrder());
        System.out.println(rsl);
    }
}