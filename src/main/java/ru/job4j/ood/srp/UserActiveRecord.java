package ru.job4j.ood.srp;

import ru.job4j.spammer.ImportDB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class UserActiveRecord {

    final private String name;

    final private String email;

    final private int age;

    public UserActiveRecord(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public void save() throws SQLException, IOException, ClassNotFoundException {
        Properties cfg = new Properties();
        try (InputStream in = UserActiveRecord.class.getClassLoader().getResourceAsStream("app.properties")) {
            cfg.load(in);
        }
        Class.forName(cfg.getProperty("jdbc.driver"));

        Connection cnt = DriverManager.getConnection(
                cfg.getProperty("jdbc.url"),
                cfg.getProperty("jdbc.username"),
                cfg.getProperty("jdbc.password")
        );

        try (PreparedStatement ps = cnt.prepareStatement("INSERT INTO users (name, email, age) VALUES (?, ?, ?)")) {
            ps.setString(1, this.name);
            ps.setString(2, this.email);
            ps.setInt(3, this.age);
            ps.execute();
        }
    }
}
