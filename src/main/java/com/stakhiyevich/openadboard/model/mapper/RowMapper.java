package com.stakhiyevich.openadboard.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * The RowMapper interface maps rows of a ResultSet on a per-row basis.
 *
 * @param <T> the entity class
 */
public interface RowMapper<T> {
    /**
     * Maps each row of data in the ResultSet.
     *
     * @param resultSet the ResultSet to map
     * @return the optional object of a result object for the current row
     * @throws SQLException if an SQLException is encountered getting column values
     */
    Optional<T> mapRow(ResultSet resultSet) throws SQLException;
}
