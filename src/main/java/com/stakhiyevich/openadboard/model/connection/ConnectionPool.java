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

/**
 * Connection pool class
 */
public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isPoolCreated = new AtomicBoolean(false);
    private static final Lock locker = new ReentrantLock(true);
    /**
     * Default size of a connection pool
     */
    private static final int DEFAULT_POOL_SIZE = 4;
    /**
     * Connection pool instance
     */
    private static ConnectionPool instance;
    /**
     * Queue of available connections
     */
    private final BlockingDeque<ProxyConnection> availableConnections;
    /**
     * Queue of used connections
     */
    private final BlockingDeque<ProxyConnection> usedConnections;

    /**
     * Initializes a Connection pool along with initialization of the available and used connection queues
     * Fills up the available connections queue
     */
    private ConnectionPool() {
        availableConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        usedConnections = new LinkedBlockingDeque<>(DEFAULT_POOL_SIZE);
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                ProxyConnection connection = ConnectionFactory.createConnection();
                availableConnections.put(connection);
            } catch (SQLException | InterruptedException e) {
                logger.error("failed to create a connection pool");
            }
        }
        if (availableConnections.isEmpty()) {
            logger.fatal("the connection pool is empty, no connections created");
            throw new RuntimeException("the connection pool is empty, no connections were created");
        }
        logger.info("connection pool has been successfully created");
    }

    /**
     * Gets instance of the connection pool.
     *
     * @return the connection pool instance
     */
    public static ConnectionPool getInstance() {
        if (!isPoolCreated.get()) {
            try {
                locker.lock();
                if (instance == null) {
                    instance = new ConnectionPool();
                    isPoolCreated.set(true);
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }

    /**
     * Takes an available connection from the ConnectionPool
     *
     * @return a connection
     */
    public Connection acquireConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            usedConnections.put(connection);
        } catch (InterruptedException e) {
            logger.error("failed to aquire available connection", e);
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    /**
     * Releases a connection
     *
     * @param connection used connection
     * @throws ConnectionPoolException if receives the wrong instance of the connection
     */
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
            logger.error("failed to release connection", e);
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Destroys the connection pool
     */
    public void destroyPool() {
        for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
            try {
                availableConnections.take().reallyClose();
            } catch (SQLException e) {
                logger.error("failed to destroy pool", e);
            } catch (InterruptedException e) {
                logger.error("failed to destroy pool", e);
                Thread.currentThread().interrupt();
            }
        }
        deregisterDrivers();
    }

    /**
     * Removes all drivers from the DriverManager's list of registered drivers
     */
    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(driver -> {
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.error("failed to deregister driver", e);
            }
        });
    }
}
