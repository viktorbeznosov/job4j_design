package ru.job4j.io.search;

import ru.job4j.io.ArgsName;
import ru.job4j.io.SearchFiles;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search {

    public Search(String[] args) {
        checkArgs(args);
    }

    private static Map<String, String> argumentsMap = new HashMap<>();

    private static void checkArgs(String[] args) {
        if (args.length < 4) {
            throw new IllegalArgumentException("Insufficient number of arguments");
        }
        ArgsName argsName = ArgsName.of(args);
        argumentsMap.put("d", argsName.get("d"));
        if (!Files.exists(Path.of(argumentsMap.get("d")))) {
            throw new IllegalArgumentException("Path does not exists");
        }
        argumentsMap.put("n", argsName.get("n"));
        argumentsMap.put("t", argsName.get("t"));
        if (!Arrays.asList("name", "mask", "regex").contains(argumentsMap.get("t"))) {
            throw new IllegalArgumentException("Incorrect search type");
        }
        argumentsMap.put("o", argsName.get("o"));
        if (!getExtension(argumentsMap.get("o"))) {
            throw new IllegalArgumentException("Logfile doesn't have extension");
        }
    }

    public void search() throws IOException {
        Path path = Path.of(argumentsMap.get("d"));
        SearchFiles searcher = new SearchFiles(condition());
        Files.walkFileTree(path, searcher);
        StringBuilder builder = new StringBuilder();
        searcher.getPaths().forEach(s -> {
            builder.append(s);
            builder.append(System.lineSeparator());
        });
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(argumentsMap.get("o")))) {
            writer.write(builder.toString());
        }
    }

    private static Predicate<Path> condition() {
        String fileName = argumentsMap.get("n");
        String searchType = argumentsMap.get("t");

        return (p) -> {
            switch (searchType) {
                case "name":
                    return fileName.equals(p.toFile().getName());
                case "regex":
                    Pattern patternRegex = Pattern.compile(fileName);
                    Matcher matcherRegex = patternRegex.matcher(p.toFile().getName());

                    return matcherRegex.find();
                case "mask":
                    String maskedFilname = fileName.replace("*", "\\w+");
                    maskedFilname = maskedFilname.replace(".", "\\.");
                    maskedFilname = maskedFilname.replace("?", "\\w");
                    Pattern patternMask = Pattern.compile(maskedFilname);
                    Matcher matcherMakk = patternMask.matcher(p.toFile().getName());

                    return matcherMakk.find();
                default:
                    return fileName.equals(p.toFile().getName());
            }
        };
    }

    private static boolean getExtension(String path) {
        String logFileName = Paths.get(path).toFile().getName();
        return logFileName.split("\\.").length > 1 && !logFileName.startsWith(".");
    }

    public static void main(String[] args) throws IOException {
        Search search = new Search(args);
        search.search();
    }

}
