package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> findAll();

    Optional<User> findUserById(Long id);

    Optional<User> findUserByEmailAndPassword(String email, String password);

    boolean isEmailExist(String email);

    boolean createUser(String name, String email, String password);

    int countAllUsers();

    List<User> findAllPaginatedUsers(int currentPage, int usersPerPage);

    boolean activateUserByHash(String hash);
}
