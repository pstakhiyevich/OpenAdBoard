package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.entity.*;
import com.stakhiyevich.openadboard.util.hasher.InMemoryDbConfig;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

class ItemDaoImplTest {

    private final static String NEW_ITEM_TITLE = "title";
    private final static BigDecimal NEW_ITEM_PRICE = BigDecimal.valueOf(123);
    private final static String NEW_ITEM_DESCRIPTION = "description";
    private final static String NEW_ITEM_CONTACT = "contact";
    private final static String NEW_ITEM_CREATE_TIME = "2022-04-02T21:13:47.657005612";
    private final static String NEW_ITEM_UPDATE_TIME = "2022-04-02T21:13:47.657005612";
    private final static String NEW_ITEM_PICTURE = "picture.png";
    private static final String EXISTED_USER_NAME = "user1";
    private static final String EXISTED_USER_EMAIL = "email1@email.com";
    private static final String EXISTED_USER_REGISTRATION_DATE = "2022-04-02T21:13:47.657005612";
    private static final String EXISTED_USER_AVATAR = "123.png";

    private static AbstractDao<Item> itemDao;
    private static InMemoryDbConfig config;
    private static Connection connection;

    private static Item newItemObject;
    private static Map<String, String[]> itemParameters;

    @BeforeAll
    static void init() {
        config = new InMemoryDbConfig();
        itemDao = new ItemDaoImpl();
        connection = config.getConnection();
        itemDao.setConnection(connection);

        Category existedCategory = new Category();
        existedCategory.setId(1);
        existedCategory.setTitle("category1");

        City existedCity = new City();
        existedCity.setId(1);
        existedCity.setTitle("city1");

        User existedUserObject = new User();
        existedUserObject.setId(1);
        existedUserObject.setName(EXISTED_USER_NAME);
        existedUserObject.setEmail(EXISTED_USER_EMAIL);
        existedUserObject.setRegistrationDate(LocalDateTime.parse(EXISTED_USER_REGISTRATION_DATE));
        existedUserObject.setAvatar(EXISTED_USER_AVATAR);
        existedUserObject.setStatus(UserStatus.INACTIVATED);
        existedUserObject.setRole(UserRole.USER);

        newItemObject = new Item();
        newItemObject.setTitle(NEW_ITEM_TITLE);
        newItemObject.setPrice(NEW_ITEM_PRICE);
        newItemObject.setDescription(NEW_ITEM_DESCRIPTION);
        newItemObject.setContact(NEW_ITEM_CONTACT);
        newItemObject.setCreateTime(LocalDateTime.parse(NEW_ITEM_CREATE_TIME));
        newItemObject.setUpdateTime(LocalDateTime.parse(NEW_ITEM_UPDATE_TIME));
        newItemObject.setPicture(NEW_ITEM_PICTURE);
        newItemObject.setCategory(existedCategory);
        newItemObject.setUser(existedUserObject);
        newItemObject.setCity(existedCity);

        itemParameters = new HashMap<>();
        itemParameters.put("search_query", new String[]{"title1"});
        itemParameters.put("selected_category", new String[]{"1"});
        itemParameters.put("selected_city", new String[]{"1"});
    }

    @BeforeEach
    void setUp() {
        config.populateDatabase(connection);
    }

    @Test
    void findAll() {
        List<Item> itemList = new ArrayList<>();
        try {
            itemList = ((ItemDaoImpl) itemDao).findAllItems();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(1, itemList.size());
    }

    @Test
    void findById() {
        Optional<Item> item;
        try {
            item = itemDao.findById(1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(item.isPresent());
    }

    @Test
    void delete() {
        boolean result;
        try {
            result = itemDao.delete(1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void create() {
        boolean result;
        try {
            result = itemDao.create(newItemObject);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void countItems() {
        int result;
        try {
            result = ((ItemDaoImpl) itemDao).countItems();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, result);
    }

    @Test
    void countItemsByUserId() {
        int result;
        try {
            result = ((ItemDaoImpl) itemDao).countItemsByUserId(1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, result);
    }

    @Test
    void findItems() {
        List<Item> items;
        try {
            items = ((ItemDaoImpl) itemDao).findPaginatedItems(1, 1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, items.size());
    }

    @Test
    void findItemsWithParameters() {
        List<Item> items;
        try {
            items = ((ItemDaoImpl) itemDao).findItemsWithParameters(itemParameters);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, items.size());
    }

    @Test
    void countItemsWithParameters() {
        int result;
        try {
            result = ((ItemDaoImpl) itemDao).countItemsWithParameters(itemParameters);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, result);
    }

    @Test
    void findItemsByUserId() {
        List<Item> result;
        try {
            result = ((ItemDaoImpl) itemDao).findItemsByUserId(1, 1, 1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void update() {
        String newTitle = "updatedTitle";
        Optional<Item> updatedObject;
        newItemObject.setId(1);
        newItemObject.setTitle(newTitle);
        try {
            updatedObject = itemDao.update(newItemObject);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(newTitle, updatedObject.get().getTitle());
    }

    @AfterAll
    static void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
