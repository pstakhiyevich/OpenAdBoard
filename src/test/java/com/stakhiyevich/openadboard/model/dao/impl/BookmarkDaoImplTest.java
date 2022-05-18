package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.entity.Bookmark;
import com.stakhiyevich.openadboard.model.InMemoryDbConfig;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

class BookmarkDaoImplTest {

    private static AbstractDao<Bookmark> bookmarkDao;
    private static InMemoryDbConfig config;
    private static Connection connection;

    private static Bookmark existingBookmarkObject;
    private static Bookmark newBookmarkObject;

    @BeforeAll
    static void init() {
        config = new InMemoryDbConfig();
        bookmarkDao = new BookmarkDaoImpl();
        connection = config.getConnection();
        bookmarkDao.setConnection(connection);

        existingBookmarkObject = new Bookmark();
        existingBookmarkObject.setUserId(1);
        existingBookmarkObject.setItemId(1);

        newBookmarkObject = new Bookmark();
        newBookmarkObject.setUserId(2);
        newBookmarkObject.setItemId(1);
    }

    @BeforeEach
    void setUp() {
        config.populateDatabase(connection);
    }

    @Test
    void delete() {
        boolean result;
        try {
            result = bookmarkDao.delete(existingBookmarkObject);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void create() {
        boolean result;
        try {
            result = bookmarkDao.create(newBookmarkObject);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void findByUserIdPaginated() {
        List<Bookmark> bookmarks;
        try {
            bookmarks = ((BookmarkDaoImpl) bookmarkDao).findByUserIdPaginated(1, 1, 1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, bookmarks.size());
    }

    @Test
    void isExist() {
        boolean result;
        try {
            result = ((BookmarkDaoImpl) bookmarkDao).isExist(1, 1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void countByUserId() {
        int result;
        try {
            result = ((BookmarkDaoImpl) bookmarkDao).countByUserId(1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, result);
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