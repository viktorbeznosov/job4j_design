package ru.job4j.ood.ocp;

public class Square {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    protected int square() {
        return side * side;
    }
}
