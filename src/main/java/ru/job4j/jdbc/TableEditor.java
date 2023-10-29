package ru.job4j.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {

    private Connection connection;

    private Properties properties;

    public TableEditor(Properties properties) throws SQLException, ClassNotFoundException {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws ClassNotFoundException, SQLException {
        String driver = properties.getProperty("hibernate.connection.driver_class");
        String url = properties.getProperty("hibernate.connection.url");
        String login = properties.getProperty("hibernate.connection.username");
        String password = properties.getProperty("hibernate.connection.password");

        Class.forName(driver);

        connection = DriverManager.getConnection(url, login, password);
    }

    public void createTable(String tableName) throws Exception {
        String sql = String.format(
                "CREATE TABLE IF NOT EXISTS %s();", tableName
        );
        execute(sql);
        System.out.println(getTableScheme(connection, tableName));
    }

    public void dropTable(String tableName) {
        String sql = String.format("drop table if exists %s;", tableName);
        execute(sql);
    }

    public void addColumn(String tableName, String columnName, String type) throws Exception {
        String sql = String.format(
                "alter table if exists %s add column if not exists %s %s",
                tableName,
                columnName,
                type
        );
        execute(sql);
        System.out.println(getTableScheme(connection, tableName));
    }

    public void dropColumn(String tableName, String columnName) throws Exception {
        String sql = String.format(
                "alter table if exists %s drop column if exists %s",
                tableName,
                columnName
        );
        execute(sql);
        System.out.println(getTableScheme(connection, tableName));
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) throws Exception {
        String sql = String.format(
                "alter table if exists %s rename column %s to %s;",
                tableName,
                columnName,
                newColumnName
        );
        execute(sql);
        System.out.println(getTableScheme(connection, tableName));
    }

    private void execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTableScheme(Connection connection, String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = this.connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "SELECT * FROM %s LIMIT 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream io = classloader.getResourceAsStream("app.properties")) {
            properties.load(io);
        }
        System.out.println(properties.getProperty("db.driver"));
        TableEditor editor = new TableEditor(properties);

        editor.createTable("test_table");
        editor.addColumn("test_table", "id", "serial primary key");
        editor.addColumn("test_table", "name", "varchar");
        editor.dropColumn("test_table", "name");
        editor.addColumn("test_table", "foo", "varchar");
        editor.renameColumn("test_table", "foo", "bar");
    }
}