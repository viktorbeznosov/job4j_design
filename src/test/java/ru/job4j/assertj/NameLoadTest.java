package ru.job4j.assertj;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NameLoadTest {
    @Test
    void checkEmpty() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::getMap)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("no data");
    }

    @Test
    void checkParseEmptyData() {
        NameLoad nameLoad = new NameLoad();
        assertThatThrownBy(nameLoad::parse)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining("is empty");
    }

    @Test
    void checkParsedDataNotContainsEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String data = "name:Ivan";
        assertThatThrownBy(() -> nameLoad.parse(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(data)
                .hasMessageContaining("not contain the symbol \"=\"");
    }

    @Test
    void checkParsedDataStartsWithEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String data = "=Some";
        assertThatThrownBy(() -> nameLoad.parse(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(data)
                .hasMessageContaining("not contain a key");

    }

    @Test
    void checkParsedDataEndsWithEqualSign() {
        NameLoad nameLoad = new NameLoad();
        String data = "name=";
        assertThatThrownBy(() -> nameLoad.parse(data))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("^.+")
                .hasMessageContaining(data)
                .hasMessageContaining("not contain a value");
    }

    @Test
    void checkCorrectParsedData() {
        NameLoad nameLoad = new NameLoad();
        String first = "first name=Sergey";
        String second = "second name=Ivan";
        Map<String, String> expected = Map.of(
                "first name", "Sergey",
                "second name", "Ivan"
        );
        nameLoad.parse(first, second);
        assertThat(nameLoad.getMap())
                .isNotEmpty()
                .containsEntry("first name", "Sergey")
                .containsEntry("second name", "Ivan")
                .containsKeys("first name", "second name")
                .containsValues("Sergey", "Ivan")
                .isEqualTo(expected);
    }
}