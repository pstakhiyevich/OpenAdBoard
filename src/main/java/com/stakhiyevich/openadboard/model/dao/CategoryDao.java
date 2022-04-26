package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Category;

import java.util.List;

/**
 * The Category Data Access Object interface.
 */
public interface CategoryDao {

    /**
     * Finds all categories.
     *
     * @return the list of categories
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Category> findAllCategories() throws DaoException;

    /**
     * Finds all categories for the current page.
     *
     * @param currentPage the current page number
     * @param categoriesPerPage the number of categories per page
     * @return the list of categories
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Category> findAllPaginatedCategories(int currentPage, int categoriesPerPage) throws DaoException;

    /**
     * Counts the number of categories.
     *
     * @return the number of found categories
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countAllCategories() throws DaoException;
}
