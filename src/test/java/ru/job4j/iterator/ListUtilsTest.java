package ru.job4j.iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ListUtilsTest {

    private List<Integer> input;

    @BeforeEach
    void setUp() {
        input = new ArrayList<>(Arrays.asList(1, 3));
    }

    @Test
    void whenAddBefore() {
        ListUtils.addBefore(input, 1, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenAddBeforeWithInvalidIndex() {
        assertThatThrownBy(() -> ListUtils.addBefore(input, 3, 2))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenAddAfter() {
        ListUtils.addAfter(input, 0, 2);
        assertThat(input).hasSize(3).containsSequence(1, 2, 3);
    }

    @Test
    void whenRemoveElementsLessThan3() {
        input.add(2);
        input.add(4);
        ListUtils.removeIf(input, e -> e < 3);
        assertThat(input)
                .hasSize(2)
                .containsSequence(3, 4)
                .allMatch(e -> e >= 3)
                .noneMatch(e -> e < 3);
    }

    @Test
    void whenRemoveElementsEquals2() {
        input.add(2);
        input.add(4);
        input.add(2);
        ListUtils.removeIf(input, e -> e == 2);
        assertThat(input)
                .hasSize(3)
                .noneMatch(e -> e == 2)
                .containsSequence(1, 3, 4);
    }

    @Test
    void whenReplaceNegativeNumbersWith0() {
        input.add(-2);
        input.add(4);
        input.add(-5);
        ListUtils.replaceIf(input, e -> e < 0, 0);
        assertThat(input)
                .containsSequence(1, 3, 0, 4, 0)
                .allSatisfy(e -> assertThat(e).isNotNegative());
    }

    @Test
    void whenRemoveEqualsOneTwoThree() {
        input.add(2);
        input.add(4);
        input.add(5);
        ListUtils.removeAll(input, List.of(1, 2, 3));
        assertThat(input)
                .hasSize(2)
                .containsSequence(4, 5)
                .doesNotContain(1, 2, 3);
    }
}