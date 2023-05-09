package ru.job4j.assertj;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withPrecision;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name)
                .isEqualTo("Sphere")
                .isNotEqualTo("Unknown object");
    }

    @Test
    void isCube() {
        Box box = new Box(8, 12);
        String name = box.whatsThis();
        assertThat(name)
                .isEqualTo("Cube")
                .isNotEqualTo("Sphere");
    }

    @Test
    void isUnknownObject() {
        Box box = new Box(15, 10);
        String name = box.whatsThis();
        assertThat(name)
                .isEqualTo("Unknown object")
                .isNotEqualTo("Sphere");
    }

    @Test
    void numberOfVerticesIs4() {
        Box box = new Box(4, 4);
        int numberOfVertices = box.getNumberOfVertices();
        assertThat(numberOfVertices)
                .isPositive()
                .isGreaterThan(3)
                .isLessThan(5)
                .isEqualTo(4);
    }

    @Test
    void numberOfVerticesIsNegative() {
        Box box = new Box(15, 4);
        int numberOfVertices = box.getNumberOfVertices();
        assertThat(numberOfVertices)
                .isNegative()
                .isEqualTo(-1);
    }

    @Test
    void objectIsExists() {
        Box box = new Box(4, 4);
        boolean isExist = box.isExist();
        assertThat(isExist).isTrue();
    }

    @Test
    void objectIsNotExists() {
        Box box = new Box(20, 4);
        boolean isExist = box.isExist();
        assertThat(isExist).isFalse();
    }

    @Test
    void areaOfSphereIs12Dot56() {
        Box box = new Box(0, 1);
        double area = box.getArea();
        assertThat(area).isCloseTo(12.56d, withPrecision(0.01d));
    }

    @Test
    void areaOfTetrahedronIs43Dot3() {
        Box box = new Box(4, 5);
        double area = box.getArea();
        assertThat(area).isCloseTo(43.3d, withPrecision(0.1d));
    }

    @Test
    void areaOfObjectIsZero() {
        Box box = new Box(40, 5);
        double area = box.getArea();
        assertThat(area).isEqualTo(0);
    }
}