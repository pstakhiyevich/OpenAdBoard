package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Item;

import java.util.List;
import java.util.Map;

public interface ItemDao {

    int countItems() throws DaoException;

    int countItemsByUserId(long userId) throws DaoException;

    List<Item> findAllItems() throws DaoException;

    List<Item> findPaginatedItems(int currentPage, int recordsPerPage) throws DaoException;

    List<Item> findItemsByUserId(long userId, int currentPage, int recordsPerPage) throws DaoException;

    List<Item> findItemsWithParameters(Map<String, String[]> parameters) throws DaoException;

    int countItemsWithParameters(Map<String, String[]> parameters) throws DaoException;
}
