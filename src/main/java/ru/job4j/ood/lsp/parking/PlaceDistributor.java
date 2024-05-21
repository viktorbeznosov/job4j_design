package ru.job4j.ood.lsp.parking;

import java.util.HashMap;
import java.util.Map;

public interface PlaceDistributor {
    final HashMap<CarType, PlaceDistributor> DISTRIBUTOR_MAP = new HashMap<CarType, PlaceDistributor>() {{
        put(CarType.PASSENGER_CAR, new PassengerCarPlaceDistributor());
        put(CarType.TRUCK, new TruckPlaceDistributor());
    }};

    public Map<CarType, Integer> distribute(Car car, Map<CarType, Integer> map) throws Exception;

    public static PlaceDistributor getInstance(CarType type) {
        return DISTRIBUTOR_MAP.get(type);
    }

}
