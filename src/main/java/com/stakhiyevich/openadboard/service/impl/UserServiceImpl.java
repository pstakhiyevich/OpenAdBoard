package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.UserDao;
import com.stakhiyevich.openadboard.model.dao.impl.UserDaoImpl;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.util.hasher.PasswordHashGenerator;
import com.stakhiyevich.openadboard.util.hasher.impl.PasswordHashGeneratorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();
    private static UserService instance;

    private final PasswordHashGenerator passwordHashGenerator = PasswordHashGeneratorImpl.getInstance();

    private UserServiceImpl() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public List<User> findAll() {
        AbstractDao userDao = new UserDaoImpl();
        List<User> users = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(userDao);
            try {
                users = ((UserDaoImpl) userDao).findALl();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to find users");
        }
        return users;
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return Optional.empty();
    }
}
