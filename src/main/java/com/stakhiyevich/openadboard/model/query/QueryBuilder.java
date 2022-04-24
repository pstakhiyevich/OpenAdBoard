package com.stakhiyevich.openadboard.model.query;

import java.util.ArrayList;
import java.util.Map;

/**
 * The QueryBuilder interface simplifies the build of SQL queries
 */
public interface QueryBuilder {
    /**
     * Builds a select sql query with specified parameters.
     *
     * @param sql the sql query
     * @param parameters entity's parameters
     * @return a sql query with parameters
     */
    String buildSelectQuery(String sql, Map<String, String[]> parameters);

    /**
     * Build a count sql query with specified parameters.
     *
     * @param sql the sql query
     * @param parameters entity's parameters
     * @return a sql query with parameters
     */
    String buildCountQuery(String sql, Map<String, String[]> parameters);

    /**
     * Extracts arguments for the prepared statement of select query from specified parameters.
     *
     * @param parameters entity's parameters
     * @return an array list of arguments
     */
    ArrayList<Object> extractSelectQueryArguments(Map<String, String[]> parameters);

    /**
     * Extracts arguments for the prepared statement of the count query from specified parameters.
     *
     * @param parameters entity's parameters
     * @return an array list of arguments
     */
    ArrayList<Object> extractCountQueryArguments(Map<String, String[]> parameters);
}