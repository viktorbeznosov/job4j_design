package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.keySet().contains(key)) {
            String message = String.format("This key: '%s' is missing", key);
            throw  new IllegalArgumentException(message);
        }
        return values.get(key);
    }

    private String[] getCheckedArgs(String str) {
        String message;
        String[] elems = str.split("=", 2);
        if (!str.startsWith("-")) {
            message = String.format("Error: This argument '%s' does not start with a '-' character", str);
            throw new IllegalArgumentException(message);
        }
        if (elems.length < 2) {
            message = String.format("Error: This argument '%s' does not contain an equal sign", str);
            throw new IllegalArgumentException(message);
        }
        elems[0] = elems[0].replaceFirst("-", "");
        if (elems[0].isEmpty()) {
            message = String.format("Error: This argument '%s' does not contain a key", str);
            throw new IllegalArgumentException(message);
        }
        if (elems[1].isEmpty()) {
            message = String.format("Error: This argument '%s' does not contain a value", str);
            throw new IllegalArgumentException(message);
        }

        return elems;
    }

    private void parse(String[] args) {
        for (String str: args) {
            String[] elems = getCheckedArgs(str);
            String key = elems[0];
            String value = elems[1];
            values.put(key, value);
        }
    }

    public static ArgsName of(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }

    public static void main(String[] args) {
        ArgsName jvm = ArgsName.of(new String[] {"-Xmx=512", "-encoding=UTF-8"});
        System.out.println(jvm.get("Xmx"));

        ArgsName zip = ArgsName.of(new String[] {"-out=project.zip", "-encoding=UTF-8"});
        System.out.println(zip.get("out"));
    }
}