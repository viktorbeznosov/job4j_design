package ru.job4j.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsageLog4j {
    private static final Logger LOG = LoggerFactory.getLogger(UsageLog4j.class.getName());

    public static void main(String[] args) {
        String name = "Viktor Beznosov";
        byte age = 44;
        short daysOnJob4j = 217;
        int maxInt = 2147483647;
        char sex = 'М';
        boolean adult = true;
        long fromEarthToSun = 149_600_000_000L;
        float height = 177.5f;
        double someDouble = 0.34535d;

        LOG.info("User info name : {}, age : {}, days in Job4j : {}, sex : {}, adult : {}, height: {}", name, age, daysOnJob4j, sex, adult, height);
        LOG.debug("Max int in Java - {}, some double number - {}", maxInt, someDouble);
    }
}