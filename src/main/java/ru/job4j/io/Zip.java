package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void checkArgs(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Root folder is null. Usage  ROOT_FOLDER.");
        }
        checkTargetFile(args[2]);
        Search.checkArgs(args);
    }

    private static void checkTargetFile(String file) {
        String[] targetFileParts = file.split("\\.");
        if (targetFileParts.length < 2 || !"zip".equals(targetFileParts[1])) {
            throw new IllegalArgumentException("Incorrect name of target zip file!");
        }
    }

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path source: sources) {
                File file = new File(source.toString());
                zip.putNextEntry(new ZipEntry(file.getName()));
                try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(file))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(target)))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            try (BufferedInputStream out = new BufferedInputStream(new FileInputStream(source))) {
                zip.write(out.readAllBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        ArgsName jvm = ArgsName.of(args);
        checkArgs(new String[]{jvm.get("d"), jvm.get("e"), jvm.get("o")});
        List<Path> files = Search.search(Paths.get(jvm.get("d")), p -> !p.toFile().getName().endsWith(jvm.get("e")))
                .stream()
                .collect(Collectors.toList());
        Zip zip = new Zip();
        zip.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );
        zip.packFiles(files, new File(jvm.get("o")));
    }
}