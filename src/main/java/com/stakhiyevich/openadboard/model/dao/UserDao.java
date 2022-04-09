package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.User;

import java.util.Optional;

public interface UserDao {
    /**
     * Finds a user with the specified email.
     *
     * @param email the user's email
     * @return the optional object of a user
     * @throws DaoException if there is any problem occurred during the data access
     */
    Optional<User> findByEmail(String email) throws DaoException;
}
