package ru.job4j.ood.lsp;

public class AutoTransport {
    protected float fuel;

    public AutoTransport(float fuel) {
        this.fuel = fuel;
    }

    protected void refuel(float fuel) throws Exception {
        this.fuel += fuel;
    }
}
