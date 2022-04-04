package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.AbstractEntity;

import java.sql.Connection;
import java.util.List;

public abstract class AbstractDao<T extends AbstractEntity> {

    protected Connection connection;

    void setConnection(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> findALl() throws DaoException;

    public abstract boolean create(T t) throws DaoException;
}
