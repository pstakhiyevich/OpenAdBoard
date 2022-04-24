package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Item;

import java.util.List;
import java.util.Map;

/**
 * The Item Data Access Object interface.
 */
public interface ItemDao {
    /**
     * Counts the number of all items.
     *
     * @return a number of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countItems() throws DaoException;

    /**
     * Counts all items with the specified user id.
     *
     * @param userId the user's id
     * @return a number of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countItemsByUserId(long userId) throws DaoException;

    /**
     * Finds all items.
     *
     * @return a list of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Item> findAllItems() throws DaoException;

    /**
     * Finds all items for the current page.
     *
     * @param currentPage the current page number
     * @param recordsPerPage the items per page number
     * @return a list of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Item> findPaginatedItems(int currentPage, int recordsPerPage) throws DaoException;

    /**
     * Finds all items for the current page with specified user id.
     *
     * @param userId the user's id
     * @param currentPage the current page number
     * @param recordsPerPage the items per page number
     * @return a list of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Item> findItemsByUserId(long userId, int currentPage, int recordsPerPage) throws DaoException;

    /**
     * Finds all items with specified parameters.
     *
     * @param parameters item's parameters
     * @return a list of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Item> findItemsWithParameters(Map<String, String[]> parameters) throws DaoException;

    /**
     * Counts all items with the specified parameters.
     *
     * @param parameters item's parameters
     * @return a number of items
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countItemsWithParameters(Map<String, String[]> parameters) throws DaoException;
}
