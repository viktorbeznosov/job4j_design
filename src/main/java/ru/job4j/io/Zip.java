package ru.job4j.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private static void checkArgs(String[] args) {
        if (!Files.exists(Paths.get(args[0])) || !Files.isDirectory(Paths.get(args[0]))) {
            throw new IllegalArgumentException("Folder is not exists");
        }
        checkExtension(args[1]);
        checkTargetFile(args[2]);
    }

    private static void checkTargetFile(String file) {
        String[] targetFileParts = file.split("\\.");
        if (targetFileParts.length < 2 || !"zip".equals(targetFileParts[1])) {
            throw new IllegalArgumentException("Incorrect name of target zip file!");
        }
    }

    private static void checkExtension(String str) {
        boolean result = true;
        if (str.length() < 2 || !str.startsWith(".")) {
            result = false;
        }
        if (result) {
            str = str.replaceFirst(".", "");
            for (char c: str.toCharArray()) {
                if (((int) c < 65 || (int) c > 90) && ((int) c < 97 || (int) c > 122)) {
                    result = false;
                    break;
                }
            }
        }

        if (!result) {
            throw new IllegalArgumentException("Invalid file extension");
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
        if (args.length < 3) {
            throw new IllegalArgumentException("Root folder is null. Usage  ROOT_FOLDER.");
        }
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