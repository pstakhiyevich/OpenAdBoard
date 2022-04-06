package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
}
