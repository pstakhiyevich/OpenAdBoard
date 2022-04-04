package com.stakhiyevich.openadboard.model.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();

    private static final Properties properties = new Properties();

    private static final String DB_PROPERTIES_PATH = "config/database.properties";
    private static final String DB_DRIVER_PROPERTY = "driver";
    private static final String DB_USER_PROPERTY = "user";
    private static final String DB_PASSWORD_PROPERTY = "password";
    private static final String DB_URL_PROPERTY = "url";

    private static final String DB_DRIVER;
    private static final String DB_USER;
    private static final String DB_PASSWORD;
    private static final String DB_URL;

    static {
        try (InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_PATH)) {
            properties.load(inputStream);
            DB_URL = properties.getProperty(DB_URL_PROPERTY);
            DB_USER = properties.getProperty(DB_USER_PROPERTY);
            DB_PASSWORD = properties.getProperty(DB_PASSWORD_PROPERTY);
            DB_DRIVER = properties.getProperty(DB_DRIVER_PROPERTY);
            Class.forName(DB_DRIVER);
        } catch (IOException e) {
            logger.error("can't read database properties", e);
            throw new RuntimeException("can't read database properties");
        } catch (ClassNotFoundException e) {
            logger.error("driver not found");
            throw new RuntimeException("driver not found");
        }
    }

    private ConnectionFactory() {
    }

    static ProxyConnection createConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD));
    }
}
