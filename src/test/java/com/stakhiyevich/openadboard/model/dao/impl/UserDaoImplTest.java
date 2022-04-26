package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.model.entity.UserStatus;
import com.stakhiyevich.openadboard.util.hasher.InMemoryDbConfig;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UserDaoImplTest {

    private static final String NEW_USER_NAME = "UserName";
    private static final String NEW_USER_EMAIL = "new_email@email.com";
    private static final String NEW_USER_AVATAR = "123.png";
    private static final String NEW_USER_PASSWORD = "123aaaAAA";
    private static final String NEW_USER_HASH = "123";

    private static final String EXISTED_USER_EMAIL = "email1@email.com";

    private static AbstractDao<User> userDao;
    private static InMemoryDbConfig config;
    private static Connection connection;

    private static User newUserObject;

    @BeforeAll
    static void init() {
        config = new InMemoryDbConfig();
        userDao = new UserDaoImpl();
        connection = config.getConnection();
        userDao.setConnection(connection);

        newUserObject = new User();
        newUserObject.setName(NEW_USER_NAME);
        newUserObject.setEmail(NEW_USER_EMAIL);
        newUserObject.setStatus(UserStatus.INACTIVATED);
        newUserObject.setRole(UserRole.USER);
        newUserObject.setRegistrationDate(LocalDateTime.now());
        newUserObject.setAvatar(NEW_USER_AVATAR);
    }

    @BeforeEach
    void setUp() {
        config.populateDatabase(connection);
    }

    @Test
    void create() {
        boolean result;
        try {
            result = ((UserDaoImpl) userDao).createUser(newUserObject, NEW_USER_PASSWORD, NEW_USER_HASH);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void isEmailExist() {
        boolean result;
        try {
            result = ((UserDaoImpl) userDao).isEmailExist(EXISTED_USER_EMAIL);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void findByEmail() {
        Optional<User> user;
        try {
            user = ((UserDaoImpl) userDao).findByEmail(EXISTED_USER_EMAIL);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void activateUserByHash() {
        boolean result;
        try {
            result = ((UserDaoImpl) userDao).activateUserByHash("FJDL238432fhjer23");
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(result);
    }

    @Test
    void findAllPaginatedUsers() {
        List<User> users;
        try {
            users = ((UserDaoImpl) userDao).findAllPaginatedUsers(1, 1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(1, users.size());
    }

    @Test
    void countAllUsers() {
        int result;
        try {
            result = ((UserDaoImpl) userDao).countAllUsers();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(2, result);
    }

    @Test
    void findAll() {
        List<User> userList = new ArrayList<>();
        try {
            userList = ((UserDaoImpl) userDao).findAllUsers();
        } catch (DaoException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(2, userList.size());
    }

    @Test
    void findById() {
        Optional<User> user;
        try {
            user = userDao.findById(1);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void update() {
        String newName = "updatedName";
        newUserObject.setId(1);
        newUserObject.setName(newName);
        Optional<User> updatedUser;
        try {
            updatedUser = userDao.update(newUserObject);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(newName, updatedUser.get().getName());
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
