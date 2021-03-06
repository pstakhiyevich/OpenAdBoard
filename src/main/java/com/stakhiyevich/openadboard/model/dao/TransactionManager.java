package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;


/**
 * The TransactionManager class manages the workflow of a database transaction.
 */
public class TransactionManager implements AutoCloseable {

    private static final Logger logger = LogManager.getLogger();

    /**
     * The Connection object.
     */
    private Connection connection;

    /**
     * Commences a transaction by acquiring an available connection from the connection pool and disabling the connection's auto-commit mode.
     *
     * @param dao the arbitrary number of Data Access Objects for the transaction
     * @throws TransactionException if there is an error occurred during a transaction's initiation
     */
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

    /**
     * Closes the transaction by releasing the connection to the connection pool and enabling the connection's auto-commit mode.
     *
     * @throws TransactionException if there is an error occurred during the transaction's completion
     */
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
        boolean result = ConnectionPool.getInstance().releaseConnection(connection);
        if (!result) {
            logger.error("failed to release a connection");
            throw new TransactionException("failed to release a connection");
        }
        connection = null;
    }

    /**
     * Commits the transaction.
     *
     * @throws TransactionException if there is an error occurred during the transaction's commit.
     */
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

    /**
     * Rollbacks the transaction.
     *
     * @throws TransactionException if there is an error occurred during the transaction's rollback
     */
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
