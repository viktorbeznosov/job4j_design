package ru.job4j.question;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Analize {

    public static Info diff(Set<User> previous, Set<User> current) {
        Map<Integer, String> previousMap = new HashMap<>();
        Map<Integer, String> currentMap = new HashMap<>();
        previous.forEach(e -> previousMap.put(e.getId(), e.getName()));
        current.forEach(e -> currentMap.put(e.getId(), e.getName()));

        int added = 0;
        for (int key: currentMap.keySet()) {
            if (!previousMap.keySet().contains(key)) {
                added++;
            }
        }

        int deleted = 0;
        for (int key: previousMap.keySet()) {
            if (!currentMap.keySet().contains(key)) {
                deleted++;
            }
        }

        List<Integer> identicalKeys = new LinkedList<>();
        for (int key: currentMap.keySet()) {
            if (previousMap.keySet().contains(key)) {
                identicalKeys.add(key);
            }
        }
        int changed = 0;
        for (int key: identicalKeys) {
            if (!previousMap.get(key).equals(currentMap.get(key))) {
                changed++;
            }
        }

        return new Info(added, changed, deleted);
    }

}