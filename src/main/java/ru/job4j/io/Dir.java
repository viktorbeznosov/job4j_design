package ru.job4j.io;

import java.io.File;

public class Dir {
    public static void main(String[] args) {
        checkArgs(args);
        File file = new File(args[0]);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Not directory %s", file.getAbsoluteFile()));
        }
        System.out.println(String.format("size : %s", file.getTotalSpace()));
        for (File subfile : file.listFiles()) {
            if (subfile.getName().endsWith(args[1])) {
                System.out.printf("Имя файла: %s. Размер файла: %d", subfile.getName(), subfile.length());
                System.out.println(System.lineSeparator());
            }
        }
    }

    public static void checkArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Root folder is null. Usage  ROOT_FOLDER.");
        }
    }
}