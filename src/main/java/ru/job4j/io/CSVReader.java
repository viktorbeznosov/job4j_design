package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CSVReader {
    public static void handle(ArgsName argsName) throws Exception {
        FileInputStream inputStream = new FileInputStream(argsName.get("path"));
        List<String> data = new ArrayList<>();
        List<String> filters = Arrays.asList(argsName.get("filter").split(","));
        String delimiter = argsName.get("delimiter");
        String out = argsName.get("out");
        Map<String, Integer> filtersMap = new HashMap<>();
        try (var scanner = new Scanner(inputStream)) {
            int count = 0;
            while (scanner.hasNext()) {
                List<String> line = Arrays.asList(scanner.nextLine().split(delimiter));
                if (count == 0) {
                    filtersMap = line.stream()
                            .filter(s -> filters.contains(s))
                            .collect(Collectors.toMap(e -> e, e -> line.indexOf(e)));
                }
                Map<String, Integer> finalFiltersMap = filtersMap;
                StringJoiner stringJoiner = new StringJoiner(delimiter);
                filters.forEach(f -> {
                    if (finalFiltersMap.containsKey(f)) {
                        int index = finalFiltersMap.get(f);
                        stringJoiner.add(line.get(index));
                    }
                });
                data.add(stringJoiner.toString());
                count++;
            }
        }
        output(data, out);
    }

    private static void output(List<String> data, String out) {
        if ("stdout".equals(out)) {
            data.forEach(System.out::println);
        } else {
            File file = new File(out);
            try (PrintStream stream = new PrintStream(new FileOutputStream(file))) {
                PrintWriter writer = new PrintWriter(out);
                data.forEach(stream::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void checkParams(ArgsName argsName) {
        if (argsName.get("path").isEmpty() || !Files.exists(Path.of(argsName.get("path")))) {
            throw new IllegalArgumentException("Incorrect path argument or CSV file does not exists");
        }

        if (argsName.get("out").isEmpty()
                || (!"stdout".equals(argsName.get("out")) && !Files.exists(Path.of(argsName.get("out"))))
        ) {
            throw new IllegalArgumentException("Incorrect out parameter or output file does not exists");
        }

        if (argsName.get("filter").isEmpty()
                || argsName.get("filter").split(",").length == 0
                || argsName.get("filter").startsWith(",")
                || argsName.get("filter").endsWith(",")
        ) {
            throw new IllegalArgumentException("Incorrect filters parameter");
        }

        if (argsName.get("delimiter").isEmpty()) {
            throw new IllegalArgumentException("Incorrect delimiter parameter");
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            throw new IllegalArgumentException("Incorrect arguments count");
        }
        ArgsName argsName = ArgsName.of(args);
        checkParams(argsName);
        handle(argsName);
    }
}