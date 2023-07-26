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

    private static void checkParams(String[] args) {
        List<String> keys = new ArrayList<>();
        if (args.length < 4) {
            throw new IllegalArgumentException("Incorrect arguments count");
        }
        for (String param: args) {
            if (!param.contains("=")) {
                throw new IllegalArgumentException("Argument doesn't contains equal sign");
            }
            String[] items = param.split("=");
            String key = items[0].replaceFirst("-", "");
            String value = items[1];
            switch (key) {
                case "path":
                    if (!Files.exists(Path.of(value))) {
                        throw new IllegalArgumentException("CSV file does not exists");
                    }
                    break;
                case "out":
                    if (!"stdout".equals(value) && !Files.exists(Path.of(value))) {
                        throw new IllegalArgumentException("Incorrect out parameter or output file does not exists");
                    }
                    break;
                case "filter":
                    if (value.split(",").length == 0 || value.startsWith(",") || value.endsWith(",")) {
                        throw new IllegalArgumentException("Incorrect filters parameter");
                    }
                    break;
                default:
                    break;
            }
            keys.add(key);
        }
        if (!keys.containsAll(List.of("path", "delimiter", "out", "filter"))) {
            throw new IllegalArgumentException("There are not enough necessary arguments");
        }
    }

    public static void main(String[] args) throws Exception {
        checkParams(args);
        ArgsName argsName = ArgsName.of(args);
        handle(argsName);
    }
}