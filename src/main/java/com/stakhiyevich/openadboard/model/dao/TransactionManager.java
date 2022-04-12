package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.ConnectionPoolException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;


public class TransactionManager implements AutoCloseable {

    private static final Logger logger = LogManager.getLogger();

    private Connection connection;

    public void beginTransaction(AbstractDao... dao) throws TransactionException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().acquireConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("failed to begin a transaction", e);
            throw new TransactionException("failed begin a transaction", e);
        }
        Arrays.stream(dao).forEach(d -> d.setConnection(connection));
    }

    @Override
    public void close() throws TransactionException {
        if (connection == null) {
            logger.error("failed to end the transaction because the connection is null");
            throw new TransactionException("failed to end the transaction because the connection is null");
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("failed to end a transaction", e);
            throw new TransactionException("failed to end a transaction", e);
        }
        try {
            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (ConnectionPoolException e) {
            logger.error("failed to release a connection", e);
            throw new TransactionException("failed to release a connection", e);
        }
        connection = null;
    }

    public void commit() throws TransactionException {
        if (connection == null) {
            logger.error("failed to commit a transaction because the connection is null");
            throw new TransactionException("failed to commit a transaction because the connection is null");
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("failed to commit a transaction", e);
            throw new TransactionException("failed to commit a transaction", e);
        }
    }

    public void rollback() throws TransactionException {
        if (connection == null) {
            logger.error("failed to rollback a transaction because the connection is null");
            throw new TransactionException("failed to rollback a transaction because the connection is null");
        }
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("failed to rollback a transaction", e);
            throw new TransactionException("failed to rollback a transaction", e);
        }
    }
}
