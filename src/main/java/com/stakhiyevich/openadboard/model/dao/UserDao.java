package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * The User Data Access Object interface.
 */
public interface UserDao {
    /**
     * Checks whether a specified email already exists.
     *
     * @param email the user's email
     * @return whether a provided email was found
     * @throws DaoException if there is any problem occurred during the data access
     */
    boolean isEmailExist(String email) throws DaoException;

    /**
     * Finds a user with the specified email.
     *
     * @param email the user's email
     * @return the optional object of a user
     * @throws DaoException if there is any problem occurred during the data access
     */
    Optional<User> findByEmail(String email) throws DaoException;

    /**
     * Finds a user with the specified email and password.
     *
     * @param email the user's email
     * @param password the user's email
     * @return the optional object of a user
     * @throws DaoException if there is any problem occurred during the data access
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    /**
     * Creates a new user from a specified user entity object and password.
     *
     * @param user a user entity object
     * @param password a user's password
     * @return whether a user was created successfully
     * @throws DaoException if there is any problem occurred during the data access
     */
    boolean createUser(User user, String password, String userHash) throws DaoException;

    /**
     * Changes a user's status to 'activated' by specified hash value.
     *
     * @param hash the user's hash value
     * @return whether a user was activated successfully
     * @throws DaoException if there is any problem occurred during the data access
     */
    boolean activateUserByHash(String hash) throws DaoException;

    /**
     * Finds all users.
     *
     * @return a list of users
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<User> findAllUsers() throws DaoException;

    /**
     * Finds all users for the current page.
     *
     * @param currentPage the current page number
     * @param usersPerPage the number of users per page
     * @return a list of users
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<User> findAllPaginatedUsers(int currentPage, int usersPerPage) throws DaoException;

    /**
     * Counts the number of all users.
     *
     * @return the number of found users
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countAllUsers() throws DaoException;

    /**
     * Updates specified user's password
     *
     * @param user a user's entity object
     * @param newPassword a new password for the user
     * @return an optional object of the updated user
     * @throws DaoException if there is any problem occurred during the data access
     */
    Optional<User> changePassword(User user, String newPassword) throws DaoException;
}
