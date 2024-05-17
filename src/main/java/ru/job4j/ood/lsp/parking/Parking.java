package ru.job4j.ood.lsp.parking;

public interface Parking {
    public void park(Car car) throws Exception;

    public int getPlaces(CarType type);
}
