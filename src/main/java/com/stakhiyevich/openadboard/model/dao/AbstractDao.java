package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.AbstractEntity;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends AbstractEntity> {

    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll() throws DaoException;

    public abstract Optional<T> findById(long id) throws DaoException;

    public abstract boolean create(T t) throws DaoException;

    public abstract Optional<T> update(T t) throws DaoException;

    public abstract boolean delete(T t) throws DaoException;

    public abstract boolean delete(long id) throws DaoException;
}
