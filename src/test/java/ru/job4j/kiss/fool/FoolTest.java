package ru.job4j.kiss.fool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class FoolTest {

    private List<Integer> multiplesThree = new ArrayList<>();
    private List<Integer> multiplesFive = new ArrayList<>();
    private List<Integer> multiplesFifteen = new ArrayList<>();
    private List<Integer> simpleNumbers = new ArrayList<>();
    private Method getAnswer;
    private Method isError;

    @BeforeEach
    public void initData() {
        try {
            getAnswer = Fool.class.getDeclaredMethod("getAnswer", int.class);
            isError = Fool.class.getDeclaredMethod("isError", int.class, String.class);
            getAnswer.setAccessible(true);
            isError.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i <= 100; i++) {
            if (i % 3 == 0 && i % 15 != 0) {
                multiplesThree.add(i);
            }
            if (i % 5 == 0 && i % 15 != 0) {
                multiplesFive.add(i);
            }
            if (i % 15 == 0) {
                multiplesFifteen.add(i);
            }
            if (i % 3 != 0 && 1 % 5 != 0) {
                simpleNumbers.add(i);
            }
        }
    }

    @Test
    void whenAnswerIsMultipleThreeIsFizz() {
        try {
            for (int answer: multiplesThree) {
                assertThat(getAnswer.invoke(Fool.class, answer)).isEqualTo("Fizz");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenAnswerIsMultipleFiveIsBuzz() {
        try {
            for (int answer: multiplesFive) {
                assertThat(getAnswer.invoke(Fool.class, answer)).isEqualTo("Buzz");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenAnswerIsMultipleFifteenIsFizzBuzz() {
        try {
            for (int answer: multiplesFifteen) {
                assertThat(getAnswer.invoke(Fool.class, answer)).isEqualTo("FizzBuzz");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtIsMultipleThreeAndAnswerIsNotFizzIsError() {
        try {
            for (int answer: multiplesThree) {
                assertTrue((Boolean) isError.invoke(Fool.class, answer, "NotFizz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtIsMultipleThreeAndAnswerIsFizzIsOk() {
        try {
            for (int answer: multiplesThree) {
                assertFalse((Boolean) isError.invoke(Fool.class, answer, "Fizz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtIsMultipleFiveAndAnswerIsNotBuzzIsError() {
        try {
            for (int answer: multiplesFive) {
                assertTrue((Boolean) isError.invoke(Fool.class, answer, "NotBuzz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtIsMultipleFiveAndAnswerIsBuzzIsOk() {
        try {
            for (int answer: multiplesFive) {
                assertFalse((Boolean) isError.invoke(Fool.class, answer, "Buzz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtIsMultipleFifteenAndAnswerIsNotFizzBuzzIsError() {
        try {
            for (int answer: multiplesFifteen) {
                assertTrue((Boolean) isError.invoke(Fool.class, answer, "NotFizzBuzz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtIsMultipleFifteenAndAnswerIsFizzBuzzIsOk() {
        try {
            for (int answer: multiplesFifteen) {
                assertFalse((Boolean) isError.invoke(Fool.class, answer, "FizzBuzz"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void whenStartAtSimpleNumberError() {
        try {
            for (int answer: simpleNumbers) {
                assertTrue((Boolean) isError.invoke(Fool.class, answer, String.valueOf(answer + 1)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}