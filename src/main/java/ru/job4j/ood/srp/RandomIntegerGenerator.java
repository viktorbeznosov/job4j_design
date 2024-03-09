package ru.job4j.ood.srp;

import java.util.Random;

public class RandomIntegerGenerator implements NumberGenerator {
    @Override
    public Integer generate() {
        Random random = new Random();
        return random.nextInt();
    }
}
