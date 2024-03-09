package ru.job4j.ood.srp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToDoSaveService implements ToDoSaverInterface {

    private List<String> items = new ArrayList<>();

    @Override
    public void addItem(String item) {
        items.add(item);
    }

    @Override
    public List<String> getItems() {
        return items;
    }

    @Override
    public void save(List<String> items) {
        try (FileWriter writer = new FileWriter("./items.txt", true)) {
            for (String item: items) {
                writer.write(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
