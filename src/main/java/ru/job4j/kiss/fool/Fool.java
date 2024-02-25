package ru.job4j.kiss.fool;

import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class Fool {

    public static String getAnswer(int currentValue) {
        String result = Integer.toString(currentValue);
        if (currentValue % 3 == 0 && currentValue % 5 == 0) {
            result = "FizzBuzz";
        } else if (currentValue % 3 == 0) {
            result = "Fizz";
        } else if (currentValue % 5 == 0) {
            result = "Buzz";
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Игра FizzBuzz.");
        var startAt = 1;
        var input = new Scanner(System.in);
        while (startAt < 100) {
            System.out.println(getAnswer(startAt));
            startAt++;
            if (!input.nextLine().equals(getAnswer(startAt))) {
                System.out.println("Ошибка. Начинай снова.");
                startAt = 0;
            }
            startAt++;
        }
    }
}