package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    boolean isEmailExist(String email) throws DaoException;

    Optional<User> findByEmail(String email) throws DaoException;

    Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException;

    boolean createUser(User user, String password, String userHash) throws DaoException;

    boolean activateUserByHash(String hash) throws DaoException;

    List<User> findAllUsers() throws DaoException;

    List<User> findAllPaginatedUsers(int currentPage, int usersPerPage) throws DaoException;

    int countAllUsers() throws DaoException;

    Optional<User> changePassword(User user, String newPassword) throws DaoException;

}
