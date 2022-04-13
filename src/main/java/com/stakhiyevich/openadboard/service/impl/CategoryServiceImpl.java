package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.CategoryDaoImpl;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryServiceImpl implements CategoryService {
    private static final Logger logger = LogManager.getLogger();
    private static CategoryService instance;

    private CategoryServiceImpl() {
    }

    public static CategoryService getInstance() {
        if (instance == null) {
            instance = new CategoryServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Category> findAllCategories() {
        AbstractDao itemCategoryDao = new CategoryDaoImpl();
        List<Category> categories = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemCategoryDao);
            try {
                categories = ((CategoryDaoImpl) itemCategoryDao).findAll();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return categories;
    }

    @Override
    public List<Category> findAllPaginatedCategories(int currentPage, int categoriesPerPage) {
        AbstractDao itemCategoryDao = new CategoryDaoImpl();
        List<Category> categories = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemCategoryDao);
            try {
                categories = ((CategoryDaoImpl) itemCategoryDao).findAllCategoriesPagination(currentPage, categoriesPerPage);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return categories;
    }

    @Override
    public int countAllCategories() {
        AbstractDao categoryDao = new CategoryDaoImpl();
        int numberOfCategories = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(categoryDao);
            try {
                numberOfCategories = ((CategoryDaoImpl) categoryDao).countAllCategories();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return numberOfCategories;
    }

    @Override
    public boolean createCategory(String categoryTitle) {
        AbstractDao categoryDao = new CategoryDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(categoryDao);
            try {
                Category category = createCategoryObject(categoryTitle);
                boolean result = categoryDao.create(category);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            }catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean updateCategory(long categoryId, String categoryTitle) {
        AbstractDao categoryDao = new CategoryDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(categoryDao);
            try {
                Optional<Category> categoryForUpdate = categoryDao.findById(categoryId);
                Category updatedCategory = updateCategoryObject(categoryForUpdate.get(), categoryTitle);
                Optional<City> result = categoryDao.update(updatedCategory);
                if (result.isPresent()) {
                    transactionManager.commit();
                    return true;
                }
            }catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean deleteCategory(long categoryId) {
        AbstractDao categoryDao = new CategoryDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(categoryDao);
            try {
                boolean result = categoryDao.delete(categoryId);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    private Category createCategoryObject(String categoryTitle) {
        Category category = new Category();
        category.setTitle(categoryTitle);
        return category;
    }

    private Category updateCategoryObject(Category categoryForUpdate, String categoryTitle) {
        categoryForUpdate.setTitle(categoryTitle);
        return categoryForUpdate;
    }
}
