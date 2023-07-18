package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {
    public static void main(String[] args) throws IOException {
        checkArgs(args);
        Path start = Paths.get(args[0]);
        search(start, p -> p.toFile().getName().endsWith(args[1])).forEach(System.out::println);
    }

    public static List<Path> search(Path root, Predicate<Path> condition) throws IOException {
        SearchFiles searcher = new SearchFiles(condition);
        Files.walkFileTree(root, searcher);
        return searcher.getPaths();
    }

    public static void checkArgs(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Root folder is null. Usage  ROOT_FOLDER.");
        }
        if (!Files.exists(Paths.get(args[0])) || !Files.isDirectory(Paths.get(args[0]))) {
            throw new IllegalArgumentException("Folder is not exists");
        }
        if (!checkExtension(args[1])) {
            throw new IllegalArgumentException("Invalid file extension");
        }
    }

    private static boolean checkExtension(String str) {
        boolean result = true;
        if (str.length() < 2 || !str.startsWith(".")) {
            result = false;
        }
        if (result) {
            str = str.replaceFirst(".", "");
            for (char c: str.toCharArray()) {
                System.out.println((int) c);
                if (((int) c < 141 || (int) c > 172) && ((int) c < 101 || (int) c > 132)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}