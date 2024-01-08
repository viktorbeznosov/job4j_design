package ru.job4j.cache;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class DirFileCache extends AbstractCache<String, String> {

    private final String cachingDir;

    public DirFileCache(String cachingDir) {
        this.cachingDir = cachingDir;
    }

    @Override
    protected String load(String key) {
        String result = null;
        String path = String.format("%s/%s", cachingDir, key);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader
                .lines()
                .forEach(s -> builder.append(s).append(System.lineSeparator()));
            result = builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}