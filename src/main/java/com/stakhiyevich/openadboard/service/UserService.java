package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The SignUp service interface.
 */
public interface UserService {
    /**
     * Finds all users.
     *
     * @return a list of users
     */
    List<User> findAll();
    /**
     * Finds a user with the specified email and password
     *
     * @param email a user's email
     * @param password a user's password
     * @return an optional object of a user
     */
    Optional<User> findUserByEmailAndPassword(String email, String password);
}
