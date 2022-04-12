package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcTemplate<T> {

    private static final Logger logger = LogManager.getLogger();

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

    public int update(Connection connection, String sql, Object[] args) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            PreparedStatementSetter.setValues(statement, args);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("failed to execute an update", e);
            throw new DaoException("failed to execute an update", e);
        }
    }
}
