package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.Category;

import java.util.List;

/**
 * The Category service interface.
 */
public interface CategoryService {

    /**
     * Finds all categories.
     *
     * @return a list of categories
     */
    List<Category> findAllCategories();

    /**
     * Finds all categories for the current page.
     *
     * @param currentPage the current page number
     * @param categoriesPerPage the categories per page number
     * @return a list of categories
     */
    List<Category> findAllPaginatedCategories(int currentPage, int categoriesPerPage);

    /**
     * Count all categories.
     *
     * @return the number of categories
     */
    int countAllCategories();

    /**
     * Creates a new category.
     *
     * @param categoryTitle the category's title
     * @return whether a category was successfully created
     */
    boolean createCategory(String categoryTitle);

    /**
     * Updates a category by specified id.
     *
     * @param categoryId category's id for update
     * @param categoryTitle new category's title
     * @return whether a category was successfully updated
     */
    boolean updateCategory(long categoryId, String categoryTitle);

    /**
     * Deletes a category by specified id.
     *
     * @param categoryId the category's id to delete
     * @return whether a category was successfully deleted
     */
    boolean deleteCategory(long categoryId);
}
