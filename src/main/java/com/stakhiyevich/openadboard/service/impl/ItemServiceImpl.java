package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.ItemDaoImpl;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.ItemService;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LogManager.getLogger();
    private static ItemService instance;

    private ItemServiceImpl() {
    }

    public static ItemService getInstance() {
        if (instance == null) {
            instance = new ItemServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addItem(String title, BigDecimal price, String description, String contact, String pictureFileName, Long categoryId, User user, Long cityId, List<Part> parts) {
        return false;
    }

    @Override
    public boolean updateItem(long id, String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts) {
        return false;
    }

    @Override
    public boolean deleteItemById(long id) {
        AbstractDao itemDao = new ItemDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                boolean result = itemDao.delete(id);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return false;
    }

    @Override
    public int countItems() {
        AbstractDao itemDao = new ItemDaoImpl();
        int numberOfItems = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                numberOfItems = ((ItemDaoImpl) itemDao).countItems();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return numberOfItems;
    }

    @Override
    public int countItemsByUserId(long userId) {
        AbstractDao itemDao = new ItemDaoImpl();
        int numberOfItems = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                numberOfItems = ((ItemDaoImpl) itemDao).countItemsByUserId(userId);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return numberOfItems;
    }

    @Override
    public List<Item> findPaginatedItems(int currentPage, int itemsPerPage) {
        AbstractDao itemDao = new ItemDaoImpl();
        List<Item> items = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                items = ((ItemDaoImpl) itemDao).findItems(currentPage, itemsPerPage);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return items;
    }

    @Override
    public List<Item> findPaginatedItemsByUserId(long userId, int currentPage, int itemsPerPage) {
        AbstractDao itemDao = new ItemDaoImpl();
        List<Item> items = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                items = ((ItemDaoImpl) itemDao).findItemsByUserId(userId, currentPage, itemsPerPage);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return items;
    }

    @Override
    public Optional<Item> findItemById(long id) {
        AbstractDao itemDao = new ItemDaoImpl();
        Optional<Item> item = Optional.empty();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                item = ((ItemDaoImpl) itemDao).findById(id);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return item;
    }

    @Override
    public boolean userCanEditItem(long userId, long itemId) {
        AbstractDao itemDao = new ItemDaoImpl();
        boolean result = false;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                long currentUserId = ((ItemDaoImpl) itemDao).findById(itemId).get().getUser().getId();
                if (currentUserId == userId) {
                    result = true;
                }
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return result;
    }
}
