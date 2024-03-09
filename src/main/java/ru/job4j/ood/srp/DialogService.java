package ru.job4j.ood.srp;

import java.util.Scanner;

public class DialogService implements InOutInterface {

    private Scanner scanner = new Scanner(System.in);

    private String name;

    @Override
    public void input() {
        System.out.println("Hou is your name?");
        name = scanner.nextLine();
    }

    @Override
    public void output() {
        System.out.printf("Hello %s", name);
    }

    public static void main(String[] args) {
        DialogService service = new DialogService();
        service.input();
        service.output();
    }
}
