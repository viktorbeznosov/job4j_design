package ru.job4j.io;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LogFilter {
    public static List<String> filter(String file) {
        List<String> result = new LinkedList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.lines()
                .filter(e -> {
                    String[] elements = e.split(" ");
                    return "404".equals(elements[elements.length - 2]);
                })
                .forEach(result::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void save(List<String> log, String file) {
        try (PrintWriter out = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(file)
                ))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<String> log = filter("data/log.txt");
        save(log, "data/404.txt");
    }
}
