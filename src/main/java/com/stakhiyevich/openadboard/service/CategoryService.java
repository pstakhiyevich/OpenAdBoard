package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllCategories();

    List<Category> findAllPaginatedCategories(int currentPage, int categoriesPerPage);

    int countAllCategories();

    boolean createCategory(String categoryTitle);

    boolean updateCategory(long categoryId, String categoryTitle);

    boolean deleteCategory(long categoryId);
}
