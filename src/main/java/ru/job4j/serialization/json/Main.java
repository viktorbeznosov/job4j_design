package ru.job4j.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    public static void main(String[] args) {
        final Person person = new Person(false, 30, new Contact("11-111"),
                new String[] {"Worker", "Married"});

        final Gson gson = new GsonBuilder().create();
        System.out.println(gson.toJson(person));

        final String personJson =
                "{"
                        + "\"sex\":false,"
                        + "\"age\":35,"
                        + "\"contact\":"
                        + "{"
                        + "\"phone\":\"+7(924)111-111-11-11\""
                        + "},"
                        + "\"statuses\":"
                        + "[\"Student\",\"Free\"]"
                        + "}";

        final Person personMod = gson.fromJson(personJson, Person.class);
        System.out.println(personMod);

        final Product notebook = new Product("Acer Aspire 1 A115-22-R2DZ", 28990, true, new String[] {"Notebooks", "Computers"}, person);
        System.out.println(gson.toJson(notebook));

        final String noteBookJson =
             "{"
                + "\"name\": \"Lenovo IdeaPad 3 15ITL05\","
                +"\"price\": 41990,"
                +"\"available\": true,"
                + "\"categories\": ["
                +    "\"Notebooks\","
                +    "\"Computers\""
                +"],"
                + "\"salesManager\": {"
                +    "\"sex\": false,"
                +        "\"age\": 30,"
                +        "\"contact\": {"
                +        "\"phone\": \"11-111\""
                +    "},"
                +    "\"statuses\": ["
                +            "\"Worker\","
                +            "\"Married\""
                +    "]"
                + "}"
            + "}";
        System.out.println(gson.fromJson(noteBookJson, Product.class));
    }
}
