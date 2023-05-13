package ru.job4j.assertj;

import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleConvertTest {
    @Test
    void checkArray() {
        SimpleConvert simpleConvert = new SimpleConvert();
        String[] array = simpleConvert.toArray("first", "second", "three", "four", "five");
        assertThat(array).hasSize(5)
                .contains("second")
                .contains("first", Index.atIndex(0))
                .containsAnyOf("zero", "second", "six")
                .doesNotContain("first", Index.atIndex(1));
        assertThat(array)
                .filteredOn(e -> e.startsWith("f"))
                .hasSize(3)
                .startsWith("first")
                .endsWith("five");
        assertThat(array)
                .filteredOnAssertions(e -> assertThat(e.length()).isEqualTo(4))
                .hasSize(2)
                .allSatisfy(e -> assertThat(e).startsWith("f"))
                .anySatisfy(e -> assertThat(e).isEqualTo("four"))
                .anySatisfy(e -> assertThat(e).isEqualTo("five"));
    }

    @Test
    void checkList() {
        SimpleConvert simpleConvert = new SimpleConvert();
        List<String> list = simpleConvert.toList("first", "second", "three", "four", "five");
        assertThat(list)
                .hasSize(5)
                .isNotEmpty()
                .allSatisfy(e -> assertThat(e.length()).isGreaterThan(3).isLessThan(7))
                .anySatisfy(e -> assertThat(e).contains("r"))
                .allMatch(e -> e.length() <= 6)
                .noneMatch(e -> e.length() > 6)
                .anyMatch(e -> e.contains("fi"))
                .containsExactly("first", "second", "three", "four", "five")
                .startsWith("first")
                .endsWith("five");
    }

    @Test
    void checkMap() {
        SimpleConvert simpleConvert = new SimpleConvert();
        Map<String, Integer> map = simpleConvert.toMap("first", "second", "three", "four", "five");
        assertThat(map).isNotEmpty()
                .containsValues(1, 2, 3)
                .containsKeys("three", "four", "five")
                .containsEntry("first", 0);
    }
}