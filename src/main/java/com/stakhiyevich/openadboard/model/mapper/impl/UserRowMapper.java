package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.UserEntity;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.model.entity.UserStatus;
import com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

public class UserRowMapper implements RowMapper<UserEntity> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<UserEntity> mapRow(ResultSet resultSet) {
        try {
            UserEntity user = new UserEntity();
            user.setId(resultSet.getLong(DatabaseColumnTitleHolder.USER_ID));
            user.setName(resultSet.getString(DatabaseColumnTitleHolder.USER_NAME));
            user.setEmail(resultSet.getString(DatabaseColumnTitleHolder.USER_EMAIL));
            user.setPassword(resultSet.getString(DatabaseColumnTitleHolder.USER_PASSWORD));
            user.setRegistrationDate(resultSet.getObject(DatabaseColumnTitleHolder.USER_REGISTRATION_DATE, LocalDateTime.class));
            user.setHash(resultSet.getString(DatabaseColumnTitleHolder.USER_HASH));
            user.setAvatar(resultSet.getString(DatabaseColumnTitleHolder.USER_AVATAR));
            String status = resultSet.getString(DatabaseColumnTitleHolder.USER_STATUS).toUpperCase();
            String role = resultSet.getString(DatabaseColumnTitleHolder.USER_ROLE).toUpperCase(Locale.ROOT);
            user.setStatus(UserStatus.valueOf(status));
            user.setRole(UserRole.valueOf(role));
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error("can't fetch data from the result set");
            return Optional.empty();
        }
    }
}
