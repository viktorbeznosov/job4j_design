package ru.job4j.ood.ocp;

public class Animal {

    private String name;

    public Animal(String name) {
        this.name = name;
    }

    public void sound() {
        if (name == "Cat") {
            System.out.println("Meow");
        } else if (name == "Dog") {
            System.out.println("Wof");
        } else {
            System.out.println("...");
        }
    }
}
