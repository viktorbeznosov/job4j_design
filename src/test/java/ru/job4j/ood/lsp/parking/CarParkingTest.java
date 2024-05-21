package ru.job4j.ood.lsp.parking;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
class CarParkingTest {
    @Test
    public void whenTruckParkingException() {
        Map<CarType, Integer> carPlaceSizesMap = new HashMap<>() {{
            put(CarType.TRUCK, 2);
            put(CarType.PASSENGER_CAR, 1);
        }};

        Parking parking = new CarParking(carPlaceSizesMap);
        Car truck = new Truck(3);

        Exception exception = assertThrows(
                Exception.class,
                () -> {
                    parking.park(truck);
                }
        );
        assertThat(exception.getMessage()).isEqualTo("There are no more places for " + truck.getType().name());
    }

    @Test
    public void whenPassengerCarParkingException() throws Exception {
        Map<CarType, Integer> carPlaceSizesMap = new HashMap<>() {{
            put(CarType.PASSENGER_CAR, 1);
        }};

        Parking parking = new CarParking(carPlaceSizesMap);
        Car passengerCar = new PassengerCar();
        parking.park(passengerCar);
        Car anotherPassengerCar = new PassengerCar();

        Exception exception = assertThrows(
                Exception.class,
                () -> {
                    parking.park(anotherPassengerCar);
                }
        );
        assertThat(exception.getMessage()).isEqualTo("There are no more places for " + anotherPassengerCar.getType().name());
    }

    @Test
    public void whenTruckParkingToTruckPlaces() throws Exception {
        Map<CarType, Integer> carPlaceSizesMap = new HashMap<>() {{
            put(CarType.TRUCK, 10);
        }};
        Parking parking = new CarParking(carPlaceSizesMap);
        Car truck = new Truck(2);

        parking.park(truck);
        assertThat(parking.getPlaces(CarType.TRUCK)).isEqualTo(8);
    }

    @Test
    public void whenTruckParkingToPassengerCarPlaces() throws Exception {
        Map<CarType, Integer> carPlaceSizesMap = new HashMap<>() {{
            put(CarType.TRUCK, 1);
            put(CarType.PASSENGER_CAR, 10);
        }};
        Parking parking = new CarParking(carPlaceSizesMap);
        Car truck = new Truck(2);
        parking.park(truck);
        assertThat(parking.getPlaces(CarType.TRUCK)).isEqualTo(1);
        assertThat(parking.getPlaces(CarType.PASSENGER_CAR)).isEqualTo(8);
    }

}