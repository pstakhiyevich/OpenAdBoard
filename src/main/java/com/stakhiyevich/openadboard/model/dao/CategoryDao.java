package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAllCategoriesPagination(int currentPage, int categoriesPerPage) throws DaoException;

    int countAllCategories() throws DaoException;
}
