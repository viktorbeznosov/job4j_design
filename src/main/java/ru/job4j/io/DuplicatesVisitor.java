package ru.job4j.io;

import ru.job4j.io.duplicates.FileProperty;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private Map<FileProperty, List<Path>> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(file.toFile().length(), file.getFileName().toString());

        if (files.get(fileProperty) != null) {
            files.get(fileProperty).add(file.toAbsolutePath());
        } else {
            List<Path> tmp = new ArrayList<>();
            tmp.add(file.toAbsolutePath());
            files.put(fileProperty, tmp);
        }

        return super.visitFile(file, attrs);
    }

    public List<Path> getPaths() {
        List<Path> paths = new LinkedList<>();

        files.entrySet()
                .stream()
                .filter(e -> e.getValue().size() > 1)
                .forEach(e -> paths.addAll(e.getValue()));

        return paths;
    }

    public static void main(String[] args) throws IOException {
        DuplicatesVisitor visitor = new DuplicatesVisitor();
        Files.walkFileTree(Path.of("./"), visitor);
        visitor.getPaths()
                .forEach(System.out::println);
    }
}
