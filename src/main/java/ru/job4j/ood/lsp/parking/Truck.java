package ru.job4j.ood.lsp.parking;

public class Truck extends AbstractCar {
    public Truck(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("The size cannot be less than 2");
        }
        this.type = CarType.TRUCK;
        this.size = size;
    }
}
