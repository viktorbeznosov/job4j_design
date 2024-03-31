package ru.job4j.ood.lsp;

public class VipAccount extends BankAccount {
    public VipAccount(String name) {
        super(name);
    }

    public void openAccount(int account) {
        if (account < 100) {
            throw new IllegalArgumentException("Минимальная сумма ждя открытия 100р");
        }

        this.account = account;
    }
}
