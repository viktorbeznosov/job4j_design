package ru.job4j.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Config {

    private final String path;
    private final Map<String, String> values = new HashMap<String, String>();

    public Config(final String path) {
        this.path = path;
    }

    Predicate<String> check = s -> {
        return s.isEmpty()
                || s.startsWith("#")
                || s.contains("=") && !s.split("=", 2)[0].isEmpty() && !s.split("=", 2)[1].isEmpty();
    };

    public void load() {
        try (BufferedReader in = new BufferedReader(new FileReader(path))) {
            in.lines()
               .peek(e -> {
                   if (!check.test(e)) {
                       throw new IllegalArgumentException("Invalid config file");
                   }
               })
               .collect(Collectors.toList())
               .stream()
               .filter(e -> !e.startsWith("#") && !e.isEmpty())
               .forEach(e -> values.put(e.split("=", 2)[0], e.split("=", 2)[1]));
        } catch (Exception e) {
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