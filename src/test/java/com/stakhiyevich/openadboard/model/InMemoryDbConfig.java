package com.stakhiyevich.openadboard.model;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class InMemoryDbConfig {

    private static final Properties properties = new Properties();

    private static final String DB_PROPERTIES_PATH = "config/database.properties";
    private static final String DB_DRIVER_PROPERTY = "driver";
    private static final String DB_USER_PROPERTY = "user";
    private static final String DB_PASSWORD_PROPERTY = "password";
    private static final String DB_URL_PROPERTY = "url";

    private static final String SQL_SCHEMA_PATH = "config/schema.sql";
    private static final String SQL_DATA_PATH = "config/data.sql";

    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String DB_URL;

    static {
        try (InputStream inputStream = InMemoryDbConfig.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_PATH)) {
            properties.load(inputStream);
            DB_URL = properties.getProperty(DB_URL_PROPERTY);
            DB_USER = properties.getProperty(DB_USER_PROPERTY);
            DB_PASSWORD = properties.getProperty(DB_PASSWORD_PROPERTY);
            String DB_DRIVER = properties.getProperty(DB_DRIVER_PROPERTY);
            Class.forName(DB_DRIVER);
        } catch (IOException e) {
            throw new RuntimeException("failed to read database properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("driver is not found");
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            ClassLoader classLoader = InMemoryDbConfig.class.getClassLoader();
            URL resource = classLoader.getResource(SQL_SCHEMA_PATH);
            RunScript.execute(connection, new FileReader(resource.getPath()));
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void populateDatabase(Connection connection) {
        try {
            ClassLoader classLoader = InMemoryDbConfig.class.getClassLoader();
            URL resource = classLoader.getResource(SQL_DATA_PATH);
            RunScript.execute(connection, new FileReader(resource.getPath()));
        } catch (SQLException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
