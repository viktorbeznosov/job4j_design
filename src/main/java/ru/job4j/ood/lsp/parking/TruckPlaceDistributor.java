package ru.job4j.ood.lsp.parking;


import java.util.Map;

public class TruckPlaceDistributor implements PlaceDistributor {

    @Override
    public Map<CarType, Integer> distribute(Car car, Map<CarType, Integer> map) throws Exception {
        Map<CarType, Integer> result = null;
        if (map.get(car.getType()) >= car.getSize()) {
            int remainingPlaces = map.get(car.getType()) - car.getSize();
            map.put(car.getType(), remainingPlaces);

            result = map;
        } else if (map.get(CarType.PASSENGER_CAR) >= car.getSize()) {
            int remainingPlaces = map.get(CarType.PASSENGER_CAR) - car.getSize();
            map.put(CarType.PASSENGER_CAR, remainingPlaces);

            result = map;
        } else {
            throw new Exception("There are no more places for " + car.getType().name());
        }

        return result;
    }
}
