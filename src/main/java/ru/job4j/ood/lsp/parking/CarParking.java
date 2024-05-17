package ru.job4j.ood.lsp.parking;

import java.util.Map;

public class CarParking implements Parking {

    private Map<CarType, Integer> carPlaceSizesMap;

    public CarParking() {

    }

    public CarParking(Map<CarType, Integer> carPlaceSizesMap) {
        this.carPlaceSizesMap = carPlaceSizesMap;
    }

    @Override
    public void park(Car car) throws Exception {
        PlaceDistributor distributor = PlaceDistributor.getInstance(car.getType());
        carPlaceSizesMap = distributor.distribute(car, carPlaceSizesMap);
    }

    @Override
    public int getPlaces(CarType type) {
        return carPlaceSizesMap.get(type);
    }
}
