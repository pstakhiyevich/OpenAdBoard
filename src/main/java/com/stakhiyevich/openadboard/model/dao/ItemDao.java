package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Item;

import java.util.List;

public interface ItemDao {

    int countItems() throws DaoException;

    int countItemsByUserId(long userId) throws DaoException;

    List<Item> findItems(int currentPage, int recordsPerPage) throws DaoException;

    List<Item> findItemsByUserId(long userId, int currentPage, int recordsPerPage) throws DaoException;
}
