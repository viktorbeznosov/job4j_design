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

    @BeforeEach
    public void initData() {
        for (int i = 0; i <= 100; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                multiplesFifteen.add(i);
            } else if (i % 5 == 0) {
                multiplesFive.add(i);
            } else if (i % 3 == 0) {
                multiplesThree.add(i);
            } else  {
                simpleNumbers.add(i);
            }
        }
    }

    @Test
    void whenAnswerIsMultipleThreeIsFizz() {
        for (int answer: multiplesThree) {
            assertThat(Fool.getAnswer(answer)).isEqualTo("Fizz");
        }
    }

    @Test
    void whenAnswerIsMultipleFiveIsBuzz() {
        for (int answer: multiplesFive) {
            assertThat(Fool.getAnswer(answer)).isEqualTo("Buzz");
        }
    }

    @Test
    void whenAnswerIsMultipleFifteenIsFizzBuzz() {
        for (int answer: multiplesFifteen) {
            assertThat(Fool.getAnswer(answer)).isEqualTo("FizzBuzz");
        }
    }

    @Test
    void whenAnswerIsSimpleAndEqualsStartAt() {
        for (int answer: simpleNumbers) {
            assertThat(Fool.getAnswer(answer)).isEqualTo(String.valueOf(answer));
        }
    }

}