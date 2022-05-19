package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.User;
import jakarta.servlet.http.Part;

import java.util.List;
import java.util.Optional;


/**
 * The SignUp service interface.
 */
public interface UserService {
    /**
     * Checks whether a specified email is already exist
     *
     * @param email a user's email
     * @return whether an email is already exist
     */
    boolean isEmailExist(String email);

    /**
     * Finds all users for the current page.
     *
     * @param currentPage a current page number
     * @param usersPerPage a users per page number
     * @return a list of users
     */
    List<User> findAllPaginatedUsers(int currentPage, int usersPerPage) throws ServiceException;

    /**
     * Finds a user with the specified email.
     *
     * @param email a user's emial
     * @return an optional object of a user
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;

    /**
     * Finds a user with the specified id.
     *
     * @param id a user's id
     * @return an optional object of a user
     */
    Optional<User> findUserById(Long id) throws ServiceException;

    /**
     * Finds a user with the specified email and password
     *
     * @param email a user's email
     * @param password a user's password
     * @return an optional object of a user
     */
    Optional<User> findUserByEmailAndPassword(String email, String password) throws ServiceException;

    /**
     * Updates a user with specified name, email and picture's parts
     *
     * @param user an entity object of a user
     * @param userName a user's name
     * @param userEmail a user's email
     * @param parts a list of parts that was received within a multipart/from-data POST request
     * @return whether a user was successfully updated
     */
    boolean updateUser(User user, String userName, String userEmail, List<Part> parts);

    /**
     * Changes a user's password with a specified new password.
     *
     * @param user an entity object of a user
     * @param newPassword a user's new password
     * @return whether a password was successfully changed
     */
    boolean changePassword(User user, String newPassword);

    /**
     * Updates user's status and role with specified user id
     *
     * @param userId a user's id
     * @param userStatus a user's new status
     * @param userRole a user's new role
     * @return whether a status and a role were successfully updated
     */
    boolean saveUserStatusAndRole(Long userId, String userStatus, String userRole);

    /**
     * Counts all users.
     *
     * @return a number of users
     */
    int countAllUsers();

    /**
     * Creates a new user with specified name, email and password
     *
     * @param name a user's name
     * @param email a user's email
     * @param password a user's password
     * @return whether a user was successfully created
     */
    boolean createUser(String name, String email, String password);

    /**
     * Changes a user's status to 'activated' by specified hash value.
     *
     * @param hash the user's hash value
     * @return whether a user was activated successfully
     */
    boolean activateUserByHash(String hash);
}

