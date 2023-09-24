package ru.job4j.io.search;

import ru.job4j.io.ArgsName;
import ru.job4j.io.SearchFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        ArgsName argsName = ArgsName.of(args);
        argumentsMap.put("d", argsName.get("d"));
        argumentsMap.put("n", argsName.get("n"));
        argumentsMap.put("t", argsName.get("t"));
        argumentsMap.put("o", argsName.get("o"));
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

    public static void main(String[] args) throws IOException {
        Search search = new Search(args);
        search.search();
    }

}
