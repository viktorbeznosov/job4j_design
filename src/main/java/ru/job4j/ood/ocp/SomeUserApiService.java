package ru.job4j.ood.ocp;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class SomeUserApiService {
    private String url;

    private String token;

    public SomeUserApiService(String url, String token) {
        this.url = url;
        this.token = token;
    }

    public User getUser(int id) {
        return new User(1, "Some user", "user@email.ru", 32);
    }

    public List<User> getUsers() {
        return Arrays.asList(
            new User(1, "Some user", "user@email.ru", 32),
            new User(2, "Another user", "another@email.ru", 33)
        );
    }
}
