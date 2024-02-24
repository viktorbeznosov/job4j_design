package ru.job4j.kiss.fool;

import java.util.Random;
import java.util.Scanner;
import java.util.function.Predicate;

public class Fool {

    private static boolean checkCurrentValue(Predicate<Integer> predicate, int value) {
        return predicate.test(value);
    }

    private static boolean checkAnswerString(Predicate<String> predicate, String value) {
        return predicate.test(value);
    }

    private static boolean checkMultipleThree(int value) {
        return checkCurrentValue(s -> s % 3 == 0, value);
    }

    private static boolean checkMultipleFive(int value) {
        return checkCurrentValue(s -> s % 5 == 0, value);
    }

    private static boolean checkMultipleFifteen(int value) {
        return checkMultipleThree(value) && checkMultipleFive(value);
    }

    private static boolean checkIsSimple(int value) {
        return !checkMultipleThree(value) && !checkMultipleFive(value);
    }

    private static boolean checkBuzz(String answer) {
        return checkAnswerString(s -> "Buzz".equals(s), answer);
    }

    private static boolean checkFizz(String answer) {
        return checkAnswerString(s -> "Fizz".equals(s), answer);
    }

    private static boolean checkFizzBuzz(String answer) {
        return checkAnswerString(s -> "FizzBuzz".equals(s), answer);
    }

    private static boolean isError(int currentValue, String answer) {
        return checkMultipleFifteen(currentValue) && !checkFizzBuzz(answer)
            || checkMultipleThree(currentValue) && !checkMultipleFive(currentValue) && !checkFizz(answer)
            || checkMultipleFive(currentValue) && !checkMultipleThree(currentValue) && !checkBuzz(answer)
            || checkIsSimple(currentValue) && !String.valueOf(currentValue).equals(answer);
    }

    private static String getAnswer(int currentValue) {
        String result = Integer.toString(currentValue);
        if (checkMultipleThree(currentValue)) {
            result = "Fizz";
        }
        if (checkMultipleFive(currentValue)) {
            result = "Buzz";
        }
        if (checkMultipleFifteen(currentValue)) {
            result = "FizzBuzz";
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
            var answer = input.nextLine();

            if (isError(startAt, answer)) {
                System.out.println("Ошибка. Начинай снова.");
                startAt = 0;
            }

            startAt++;
        }
    }
}