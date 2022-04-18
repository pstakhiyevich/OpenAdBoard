package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.CategoryDaoImpl;
import com.stakhiyevich.openadboard.model.dao.impl.CityDaoImpl;
import com.stakhiyevich.openadboard.model.dao.impl.ItemDaoImpl;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.UploadService;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public boolean addItem(String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts) {
        AbstractDao itemDao = new ItemDaoImpl();
        AbstractDao categoryDao = new CategoryDaoImpl();
        AbstractDao cityDao = new CityDaoImpl();
        UploadService uploadService = new UploadServiceImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao, categoryDao, cityDao);
            try {
                Optional<Category> category = categoryDao.findById(categoryId);
                Optional<City> city = cityDao.findById(cityId);
                if (category.isEmpty() || city.isEmpty()) {
                    return false;
                }
                String pictureFileName = parts.get(0).getSubmittedFileName();
                Item item = createItemObject(title, price, description, contact, pictureFileName, category.get(), user, city.get());
                String pictureFullPath = uploadService.uploadFile(parts, pictureFileName).get();
                item.setPicture(pictureFullPath);
                boolean result = itemDao.create(item);
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
    public boolean updateItem(long id, String title, BigDecimal price, String description, String contact, Long categoryId, User user, Long cityId, List<Part> parts) {
        AbstractDao itemDao = new ItemDaoImpl();
        AbstractDao categoryDao = new CategoryDaoImpl();
        AbstractDao cityDao = new CityDaoImpl();
        UploadService uploadService = new UploadServiceImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao, categoryDao, cityDao);
            try {
                Optional<Category> category = categoryDao.findById(categoryId);
                Optional<City> city = cityDao.findById(cityId);
                Optional<Item> currentItem = itemDao.findById(id);
                if (category.isEmpty() || city.isEmpty() || currentItem.isEmpty()) {
                    return false;
                }
                String pictureName;
                if (parts.isEmpty()) {
                    pictureName = currentItem.get().getPicture();
                } else {
                    pictureName = uploadService.uploadFile(parts, parts.get(0).getSubmittedFileName()).get();
                    uploadService.deleteFile(currentItem.get().getPicture());
                }
                Item updatedItem = updateItemObject(currentItem.get(), title, price, description, contact, pictureName, category.get(), user, city.get());
                Optional<Item> result = itemDao.update(updatedItem);
                if (result.isPresent()) {
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
    public boolean deleteItemById(long id) {
        AbstractDao itemDao = new ItemDaoImpl();
        UploadService uploadService = new UploadServiceImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                boolean result = itemDao.delete(id);
                if (result) {
                    uploadService.deleteFile(id);
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
    public List<Item> findItemsWithParameters(Map<String, String[]> parameters) {
        AbstractDao itemDao = new ItemDaoImpl();
        List<Item> items = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                items = ((ItemDaoImpl) itemDao).findItemsWithParameters(parameters);
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
    public int countItemsWithParameters(Map<String, String[]> parameters) {
        AbstractDao itemDao = new ItemDaoImpl();
        int numberOfItems = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                numberOfItems = ((ItemDaoImpl) itemDao).countItemsWithParameters(parameters);
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
                items = ((ItemDaoImpl) itemDao).findPaginatedItems(currentPage, itemsPerPage);
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
    public boolean canUserEditItem(long userId, long itemId) {
        AbstractDao itemDao = new ItemDaoImpl();
        boolean result = false;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(itemDao);
            try {
                Optional<Item> currentItem = ((ItemDaoImpl) itemDao).findById(itemId);
                if (currentItem.isEmpty()) {
                    return false;
                }
                if (currentItem.get().getUser().getId() == userId) {
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

    private Item createItemObject(String title, BigDecimal price, String description, String contact, String picture, Category category, User user, City city) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setDescription(description);
        item.setContact(contact);
        item.setCreateTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        item.setPicture(picture);
        item.setCategory(category);
        item.setUser(user);
        item.setCity(city);
        return item;
    }

    private Item updateItemObject(Item item, String title, BigDecimal price, String description, String contact, String pictureFileName, Category category, User user, City city) {
        item.setTitle(title);
        item.setPrice(price);
        item.setDescription(description);
        item.setContact(contact);
        item.setUpdateTime(LocalDateTime.now());
        item.setPicture(pictureFileName);
        item.setCategory(category);
        item.setUser(user);
        item.setCity(city);
        return item;
    }
}
