package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {

    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.keySet().contains(key)) {
            StringBuilder builder = new StringBuilder();
            builder.append("This key: ").append("'").append(key).append("'").append(" is missing");
            throw  new IllegalArgumentException(builder.toString());
        }
        return values.get(key);
    }

    private void checkArgs(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Arguments not passed to program");
        }
        for (String str: args) {
            StringBuilder builder = new StringBuilder();
            builder.append("Error: This argument ").append("'").append(str).append("'");
            String[] elems = str.split("=", 2);
            if (!str.startsWith("-")) {
                builder.append(" does not start with a '-' character");
                throw new IllegalArgumentException(builder.toString());
            }
            if (elems.length < 2) {
                builder.append(" does not contain an equal sign");
                throw new IllegalArgumentException(builder.toString());
            }
            if (elems[0].replaceFirst("-", "").isEmpty()) {
                builder.append(" does not contain a key");
                throw new IllegalArgumentException(builder.toString());
            }
            if (elems[1].isEmpty()) {
                builder.append(" does not contain a value");
                throw new IllegalArgumentException(builder.toString());
            }
        }
    }

    private void parse(String[] args) {
        checkArgs(args);
        for (String str: args) {
            String[] elems = str.split("=", 2);
            String key = elems[0].replaceFirst("-", "");
            String value = elems[1];
            values.put(key, value);
        }
    }

    public static ArgsName of(String[] args) {
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