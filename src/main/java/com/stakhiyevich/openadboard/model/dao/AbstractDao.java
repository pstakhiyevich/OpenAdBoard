package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.AbstractEntity;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

/**
 * The abstract Data Access object class.
 *
 * @param <T> the entity class extended from the AbstractEntity
 */
public abstract class AbstractDao<T extends AbstractEntity> {
    /**
     * The Connection object for executing SQL statements.
     */
    protected Connection connection;

    /**
     * Sets the connection.
     *
     * @param connection the connection object
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Find entity with the provided id.
     *
     * @param id the entity's id
     * @return an optional object of a given entity
     * @throws DaoException if there is any problem occurred during the data access
     */
    public abstract Optional<T> findById(long id) throws DaoException;

    /**
     * Creates a new entity.
     *
     * @param t the entity's object
     * @return whether an entity was created successfully
     * @throws DaoException if there is any problem occurred during the data access
     */
    public abstract boolean create(T t) throws DaoException;

    /**
     * @param t the entity's object
     * @return the optional object of an updated entity
     * @throws DaoException if there is any problem occurred during the data access
     */
    public abstract Optional<T> update(T t) throws DaoException;

    /**
     * @param t the entity's object
     * @return whether an entity was deleted successfully
     * @throws DaoException if there is any problem occurred during the data access
     */
    public abstract boolean delete(T t) throws DaoException;

    /**
     * @param id the entity's id
     * @return whether an entity was deleted successfully
     * @throws DaoException if there is any problem occurred during the data access
     */
    public abstract boolean delete(long id) throws DaoException;
}
