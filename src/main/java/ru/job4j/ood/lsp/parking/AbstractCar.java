package ru.job4j.ood.lsp.parking;

public abstract class AbstractCar implements Car {
    protected int size;

    protected CarType type;

    @Override
    public CarType getType() {
        return type;
    }

    @Override
    public int getSize() {
        return size;
    }
}
