package com.stakhiyevich.openadboard.model.connection;

import com.stakhiyevich.openadboard.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();

    private static ConnectionPool instance;
    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static final Lock locker = new ReentrantLock(true);

    private static final int DEFAULT_POOL_SIZE = 32;

    private final BlockingDeque<ProxyConnection> availableConnections;
    private final BlockingDeque<ProxyConnection> usedConnections;

    private ConnectionPool() {
        availableConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        usedConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = ConnectionFactory.createConnection();
                availableConnections.put(connection);
            } catch (SQLException | InterruptedException e) {
                logger.fatal("can't create connection pool");
            }
        }

        if (availableConnections.isEmpty()) {
            logger.fatal("the connection pool is empty, no connections created");
            throw new RuntimeException("the connection pool is empty, no connections created");
        }
        logger.info("connection pool has been successfully created");
    }

    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            locker.lock();
            if (instance == null) {
                instance = new ConnectionPool();
                isPoolCreated.set(true);
            }
            locker.unlock();
        }
        return instance;
    }

    public Connection acquireConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("can't quire available connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection == null) {
            logger.error("connection is null");
        }
        if (!(connection instanceof ProxyConnection)) {
            logger.error("wrong instance");
            throw new ConnectionPoolException("wrong instance");
        }
        try {
            usedConnections.remove(connection);
            availableConnections.put((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.error("can't release connection", e);
            Thread.currentThread().interrupt();
        }
    }

    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                availableConnections.take().reallyClose();
            } catch (SQLException e) {
                logger.error("can't destroy pool", e);
            } catch (InterruptedException e) {
                logger.error("can't destroy pool", e);
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("can't deregister driver", e);
            }
        });
    }
}
