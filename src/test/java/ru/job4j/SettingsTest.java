package ru.job4j;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {

    @Test
    public void whenClassLoader() throws Exception {
        Settings settings = new Settings();
        ClassLoader loader = Settings.class.getClassLoader();
        try (InputStream io = loader.getResourceAsStream("app.properties")) {
            settings.load(io);
        }
        String value = settings.getValue("hibernate.connection.driver_class");
        assertThat(value.equals("org.postgresql.Driver"));
    }
}