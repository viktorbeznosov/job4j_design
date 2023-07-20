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

    private String[] getCheckedArgs(String str) {
        String message = "Error: This argument '" + str + "'";
        String[] elems = str.split("=", 2);
        if (!str.startsWith("-")) {
            message += " does not start with a '-' character";
            throw new IllegalArgumentException(message);
        }
        if (elems.length < 2) {
            message += " does not contain an equal sign";
            throw new IllegalArgumentException(message);
        }
        elems[0] = elems[0].replaceFirst("-", "");
        if (elems[0].isEmpty()) {
            message += " does not contain a key";
            throw new IllegalArgumentException(message);
        }
        if (elems[1].isEmpty()) {
            message += " does not contain a value";
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