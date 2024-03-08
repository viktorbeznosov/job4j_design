package ru.job4j.template;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.job4j.map.SimpleMap;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@Disabled("Тесты отключены. Удалить аннотацию после реализации всех методов по заданию.")
class TemplateGeneratorTest {

    @Test
    public void whenCorrectTemplateGenerate() {
        TemplateGenerator generator = new TemplateGenerator();
        String inputString = "I am a ${name}, Who are ${subject}?";
        Map<String, String> args = new HashMap<>();
        args.put("name", "Petr Arsentev");
        args.put("subject", "you");
        assertThat(generator.produce(inputString, args)).isEqualTo("I am a Petr Arsentev, Who are you?");
    }

    @Test
    public void whenNotEnoughArgumentsThenGetException() {
        TemplateGenerator generator = new TemplateGenerator();
        String inputString = "I am a ${name}, Who are ${subject}?";
        Map<String, String> args = new HashMap<>();
        args.put("name", "Petr Arsentev");
        assertThatThrownBy(() -> generator.produce(inputString, args)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void whenIncorrectArgumentsThenGetException() {
        TemplateGenerator generator = new TemplateGenerator();
        String inputString = "I am a ${name}, Who are ${subject}?";
        Map<String, String> args = new HashMap<>();
        args.put("foo", "bar");
        args.put("incorrect", "argument");
        assertThatThrownBy(() -> generator.produce(inputString, args)).isInstanceOf(IllegalArgumentException.class);
    }
}