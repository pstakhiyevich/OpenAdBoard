package com.stakhiyevich.openadboard.model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * An adapter for the PreparedStatement object that applies a given array of arguments.
 */
public class PreparedStatementSetter {
    /**
     * Sets values for a prepared statement using passed in arguments.
     *
     * @param preparedStatement the PreparedStatement object
     * @param args the array object of arguments for the PreparedStatement
     * @throws SQLException if thorn by the PreparedStatement methods
     */
    public static void setValues(PreparedStatement preparedStatement, Object[] args) throws SQLException {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
        }
    }
}