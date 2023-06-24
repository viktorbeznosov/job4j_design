package ru.job4j.question;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, String> previousMap = previous.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getName()));
        Map<Integer, String> currentMap = current.stream().collect(Collectors.toMap(e -> e.getId(), e -> e.getName()));

        int added = (int) currentMap
                .keySet()
                .stream()
                .filter(e -> !previousMap.keySet().contains(e))
                .count();

        int deleted = (int) previousMap
                .keySet()
                .stream()
                .filter(e -> !currentMap.keySet().contains(e))
                .count();

        int changed = (int) currentMap
                .keySet()
                .stream()
                .filter(previousMap.keySet()::contains)
                .collect(Collectors.toList())
                .stream()
                .filter(e -> !previousMap.get(e).equals(currentMap.get(e)))
                .count();

        return new Info(added, changed, deleted);
    }

}