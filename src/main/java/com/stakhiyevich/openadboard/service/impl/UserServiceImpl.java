package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.UserDaoImpl;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public List<User> findAll() {
        AbstractDao<User> userDao = new UserDaoImpl();
        TransactionManager transactionManager = new TransactionManager();
        List<User> users = new ArrayList<>();
        try {
            transactionManager.beginTransaction(userDao);
            try {
                users = userDao.findALl();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            } finally {
                transactionManager.endTransaction();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return users;
    }
}
