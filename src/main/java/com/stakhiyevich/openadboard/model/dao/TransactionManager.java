package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.ConnectionPoolException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.connection.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class TransactionManager {

    private static final Logger logger = LogManager.getLogger();

    private Connection connection;

    public void beginTransaction(AbstractDao... dao) throws TransactionException {
        if (connection == null) {
            connection = ConnectionPool.getInstance().acquireConnection();
        }
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("can't begin transaction", e);
            throw new TransactionException("can't begin transaction", e);
        }
        Arrays.stream(dao).forEach(d -> d.setConnection(connection));
    }

    public void endTransaction() throws TransactionException {
        if (connection == null) {
            logger.error("can't end transaction because the connection is null");
            throw new TransactionException("can't end transaction because the connection is null");
        }
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("can't end transaction", e);
            throw new TransactionException("can't end transaction", e);
        }
        try {
            ConnectionPool.getInstance().releaseConnection(connection);
        } catch (ConnectionPoolException e) {
            logger.error("can't release connection", e);
            throw new TransactionException("can't release connection", e);
        }
        connection = null;
    }

    public void commit() throws TransactionException {
        if (connection == null) {
            logger.error("can't commit transaction because the connection is null");
            throw new TransactionException("can't commit transaction because the connection is null");
        }
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("can't commit transaction", e);
            throw new TransactionException("can't commit transaction", e);
        }
    }

    public void rollback() throws TransactionException {
        if (connection == null) {
            logger.error("can't rollback transaction because the connection is null");
            throw new TransactionException("can't rollback transaction because the connection is null");
        }
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("can't rollback transaction", e);
            throw new TransactionException("can't rollback transaction", e);
        }
    }
}
