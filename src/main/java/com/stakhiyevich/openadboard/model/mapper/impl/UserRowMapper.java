package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.model.entity.UserStatus;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.*;

public class UserRowMapper implements RowMapper<User> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<User> mapRow(ResultSet resultSet) {
        try {
            User user = new User();
            user.setId(resultSet.getLong(USER_ID));
            user.setName(resultSet.getString(USER_NAME));
            user.setEmail(resultSet.getString(USER_EMAIL));
            user.setRegistrationDate(resultSet.getObject(USER_REGISTRATION_DATE, LocalDateTime.class));
            user.setAvatar(resultSet.getString(USER_AVATAR));
            String status = resultSet.getString(USER_STATUS).toUpperCase();
            String role = resultSet.getString(USER_ROLE).toUpperCase(Locale.ROOT);
            user.setStatus(UserStatus.valueOf(status));
            user.setRole(UserRole.valueOf(role));
            return Optional.of(user);
        } catch (SQLException e) {
            logger.error("failed to fetch data from the result set");
            return Optional.empty();
        }
    }
}
