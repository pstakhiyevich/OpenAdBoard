package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.User;

import java.util.Optional;

public interface UserDao {
    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    boolean isEmailExist(String email) throws DaoException;

    boolean createUser(User user, String password, String userHash) throws DaoException;
}
