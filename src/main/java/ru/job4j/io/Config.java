package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    BiPredicate<String, String[]> check = (s, elements) -> {
        return s.isEmpty()
                || s.startsWith("#")
                || s.contains("=") && !elements[0].isEmpty() && !elements[1].isEmpty();
    };

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            in.lines()
               .filter(e -> !e.startsWith("#") && !e.isEmpty())
               .forEach(e -> {
                   String[] lineElements = e.split("=", 2);
                   if (!check.test(e, lineElements)) {
                       throw new IllegalArgumentException("Invalid config file");
                   }
                   values.put(lineElements[0], lineElements[1]);
               });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(this.path))) {
            read.lines().forEach(out::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        Config config = new Config("data/app.properties");
        config.load();
        System.out.println(config.value("hibernate.connection.password"));
    }

}