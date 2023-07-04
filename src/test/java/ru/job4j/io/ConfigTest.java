package ru.job4j.io;

import org.assertj.core.api.Assert;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ConfigTest {

    @Test
    void whenPairWithoutComment() {
        String path = "./data/pair_without_comment.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo("Petr Arsentev");
    }

    @Test
    void whenPairWithCommentsAndEmptyStringsWithoutData() {
        String path = "./data/pair_with_comments_and_empty_strings.properties";
        Config config = new Config(path);
        config.load();
        assertThat(config.value("name")).isEqualTo(null);
    }

    @Test
    void whenPairWithStringWithoutKey() {
        String path = "./data/pair_with_string_without_key.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid config file");
    }

    @Test
    void whenPairWithStringWithoutValue() {
        String path = "./data/pair_with_string_without_value.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid config file");
    }

    @Test
    void whenPairWithoutEqualSign() {
        String path = "./data/pair_without_equal_sign.properties";
        Config config = new Config(path);
        assertThatThrownBy(config::load)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid config file");
    }
}