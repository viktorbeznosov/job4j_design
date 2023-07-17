package ru.job4j.io;

import ru.job4j.io.duplicates.FileProperty;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.stream.Collectors;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private Map<FileProperty, List<Path>> files = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileProperty fileProperty = new FileProperty(file.toFile().length(), file.getFileName().toString());
        files.computeIfAbsent(fileProperty, key -> new ArrayList<>()).add(file.toAbsolutePath());
        return super.visitFile(file, attrs);
    }

    public List getPaths() {
        return files.values()
                .stream()
                .filter(e -> e.size() > 1)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        DuplicatesVisitor visitor = new DuplicatesVisitor();
        Files.walkFileTree(Path.of("./"), visitor);
        visitor.getPaths()
                .forEach(System.out::println);
    }
}
