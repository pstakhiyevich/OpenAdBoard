package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAll();
}
