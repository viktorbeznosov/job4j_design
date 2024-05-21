package ru.job4j.ood.lsp.parking;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PassengerCarTest {

    @Test
    public void whenCarTypeIsPassengerCar() {
        Car car = new PassengerCar();
        assertThat(car.getType()).isEqualTo(CarType.PASSENGER_CAR);
    }

    @Test
    public void whenCarSizeIs1() {
        Car car = new PassengerCar();
        assertThat(car.getSize()).isEqualTo(1);
    }

}