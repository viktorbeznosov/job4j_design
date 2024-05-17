package ru.job4j.ood.lsp.parking;


import java.util.Map;

public class PassengerCarPlaceDistributor implements PlaceDistributor {

    @Override
    public Map<CarType, Integer> distribute(Car car, Map<CarType, Integer> map) throws Exception {
        if (map.get(car.getType()) < car.getSize()) {
            throw new Exception("There are no more places for " + car.getType().name());
        }
        int remainingPlaces = map.get(car.getType()) - car.getSize();
        map.put(car.getType(), remainingPlaces);

        return map;
    }

}
