package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class simplifies the use of JDBC by executing JDBC common workflow, leaving application code to provide SQL and extract results.
 * It executes SQL queries or updates with different parameters.
 *
 * @param <T> the entity class
 */
public class CustomJdbcTemplate<T> {

    private static final Logger logger = LogManager.getLogger();

    /**
     * Queries given SQL to create a prepared statement from SQL and a list of arguments to bind to the query, mapping each row to a result object vie a RowMapper.
     *
     * @param connection the Connection object
     * @param sql the SQL query to execute
     * @param arg arguments to bind to the query
     * @param rowMapper a mapper that will map one object per row
     * @return the result List that contains mapped objects
     * @throws DaoException if the query fails
     */
    public List<T> query(Connection connection, String sql, Object[] arg, RowMapper<T> rowMapper) throws DaoException {
        List<T> resultList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            PreparedStatementSetter.setValues(statement, arg);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Optional<T> entity = rowMapper.mapRow(resultSet);
                    resultList.add(entity.get());
                }
            }
        } catch (SQLException e) {
            logger.error("failed to execute a query", e);
            throw new DaoException("failed to execute a query", e);
        }
        return resultList;
    }

    /**
     * Executes a query given static SQL as well as reads the ResultSet.
     *
     * @param connection the Connection object
     * @param sql the SQL query to execute
     * @return an integer value from the ResultSet
     * @throws DaoException if the query fails
     */
    public int query(Connection connection, String sql) throws DaoException {
        int result = 0;
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("failed to execute a query", e);
            throw new DaoException("failed to execute a query", e);
        }
        return result;
    }

    /**
     * Queries given SQL to create a prepared statement from SQL and a list of arguments to bind to the query as well as reads the ResultSet.
     *
     * @param connection the Connection object
     * @param sql the SQL query to execute
     * @param args arguments to bind to the query
     * @return an integer value from the ResultSet
     * @throws DaoException if the query fails
     */
    public int query(Connection connection, String sql, Object[] args) throws DaoException {
        int result = 0;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            PreparedStatementSetter.setValues(statement, args);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    result = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            logger.error("failed to execute a query", e);
            throw new DaoException("failed to execute a query", e);
        }
        return result;
    }

    /**
     * Executes a query given static SQL, mapping each row to a result object via a RowMapper.
     *
     * @param connection the Connection object
     * @param sql the SQL query to execute
     * @param rowMapper a mapper that will map one object per row
     * @return the result List that contains mapped objects
     * @throws DaoException if the query fails
     */
    public List<T> query(Connection connection, String sql, RowMapper<T> rowMapper) throws DaoException {
        List<T> resultList = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Optional<T> entity = rowMapper.mapRow(resultSet);
                    resultList.add(entity.get());
                }
            }
        } catch (SQLException e) {
            logger.error("failed to execute a query", e);
            throw new DaoException("failed to execute a query", e);
        }
        return resultList;
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete statement) via a prepared statement, binding the given arguments.
     *
     * @param connection the Connection object
     * @param sql the SQL query to execute
     * @param args arguments to bind to the query
     * @return the number of rows affected
     * @throws DaoException if there is any problem issuing the update
     */
    public int update(Connection connection, String sql, Object[] args) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            PreparedStatementSetter.setValues(statement, args);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to execute an update", e);
            throw new DaoException("failed to execute an update", e);
        }
    }

    /**
     * Issue a single SQL update operation (such as an insert, update or delete statement) via a prepared statement, binding the given arguments.
     *
     * @param connection the Connection object
     * @param sql the SQL query to execute
     * @param args arguments to bind to the query
     * @param t the entity object
     * @return the optional object of an inserted, updated or deleted entity
     * @throws DaoException if there is any problem issuing the update
     */
    public Optional<T> update(Connection connection, String sql, Object[] args, T t) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            PreparedStatementSetter.setValues(statement, args);
            if (statement.executeUpdate() >= 0) {
                return Optional.of(t);
            }
        } catch (SQLException e) {
            logger.error("failed to execute an update", e);
            throw new DaoException("failed to execute an update", e);
        }
        return Optional.empty();
    }
}
