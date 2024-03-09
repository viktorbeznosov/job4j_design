package ru.job4j.ood.srp;

import java.util.List;

public interface ToDoSaverInterface {
    public void addItem(String item);

    public List<String> getItems();

    public void save(List<String> items);
}
