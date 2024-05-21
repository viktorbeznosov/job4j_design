package ru.job4j.ood.lsp.parking;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TruckTest {

    @Test
    public void whenException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    Car truck = new Truck(1);
                }
        );
        assertThat(exception.getMessage()).isEqualTo("The size cannot be less than 2");
    }

    @Test
    public void whenTypeIsTruck() {
        Car truck = new Truck(2);
        assertThat(truck.getType()).isEqualTo(CarType.TRUCK);
    }

}