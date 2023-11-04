package ru.job4j.jdbc;
import ru.job4j.io.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DebugDemo {

    private Connection con;

    public DebugDemo() throws Exception {
        initConnection();
    }

    private void initConnection() throws Exception {
        Config config = new Config("data/app.properties");
        config.load();
        String driver = config.value("hibernate.connection.driver_class");
        String url = config.value("hibernate.connection.url");
        String login = config.value("hibernate.connection.username");
        String password = config.value("hibernate.connection.password");
        Class.forName("org.postgresql.Driver");

        con = DriverManager.getConnection(url, login, password);
    }

    public void createTable() {
        try (Statement statement = con.createStatement()) {
            statement.execute(
                    "create table if not exists cities(id serial primary key, name text, population int);"
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public City insert(City city) {
        try (PreparedStatement statement = con.prepareStatement(
                "insert into cities(name, population) values (?, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, city.getName());
            statement.setInt(2, city.getPopulation());
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    city.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("select * from cities;");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cities.add(new City(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("population")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cities;
    }

    public static void main(String[] args) throws Exception {
        City first = new City("CityOne", 100);
        City second = new City("CityTwo", 200);
        DebugDemo debugDemo = new DebugDemo();
        debugDemo.createTable();
        debugDemo.insert(first);
        debugDemo.insert(second);
        for (City city : debugDemo.findAll()) {
            System.out.println("city = " + city);
        }
    }

}