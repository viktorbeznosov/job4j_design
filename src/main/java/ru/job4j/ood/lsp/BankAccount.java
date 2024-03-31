package ru.job4j.ood.lsp;

public class BankAccount {
    protected int account = 0;

    protected String name;

    public BankAccount(String name) {
        this.name = name;
    }

    public void openAccount(int account) {
        if (account < 0) {
            throw new IllegalArgumentException("Счет не может быть меньше нуля!");
        }

        this.account = account;
    }
}
