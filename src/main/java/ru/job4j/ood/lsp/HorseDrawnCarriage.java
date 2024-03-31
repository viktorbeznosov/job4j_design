package ru.job4j.ood.lsp;

public class HorseDrawnCarriage extends AutoTransport {
    public HorseDrawnCarriage(float fuel) {
        super(fuel);
    }

    protected void refuel(float fuel) {
        System.out.println("The horse died!");
    }
}
