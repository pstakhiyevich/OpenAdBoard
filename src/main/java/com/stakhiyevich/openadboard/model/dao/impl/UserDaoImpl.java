package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.UserDao;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import com.stakhiyevich.openadboard.model.mapper.impl.UserRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_FIND_ALL_USERS = "SELECT users.id, users.name, users.email, users.password, users.registration_date, users.hash, users.avatar, user_statuses.title, user_roles.title " +
            "FROM users " +
            "JOIN user_statuses ON users.user_statuses_id = user_statuses.id " +
            "JOIN user_roles ON users.user_roles_id = user_roles.id";
    private static final String SQL_FIND_BY_EMAIL =
            "SELECT users.id, users.name, users.email, users.password, users.registration_date, users.hash, users.avatar, user_statuses.title, user_roles.title " +
                    "FROM users " +
                    "JOIN user_statuses ON users.user_statuses_id = user_statuses.id " +
                    "JOIN user_roles ON users.user_roles_id = user_roles.id " +
                    "WHERE email = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT users.id, users.name, users.email, users.registration_date, users.hash, users.avatar, user_statuses.title, user_roles.title " +
                    "FROM users " +
                    "JOIN user_statuses ON users.user_statuses_id = user_statuses.id " +
                    "JOIN user_roles ON users.user_roles_id = user_roles.id " +
                    "WHERE users.email = ? AND users.password = ?";

    private final RowMapper<User> userMapper = new UserRowMapper();

    @Override
    public List<User> findALl() throws DaoException {
        List<User> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(userMapper.mapRow(resultSet).orElse(null));
                }
            }
        } catch (SQLException e) {
            logger.error("can't find users", e);
            throw new DaoException("can't find users", e);
        }
        return result;
    }

    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public Optional<User> findByEmail(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_EMAIL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("can't find user by email", e);
            throw new DaoException("can't find user by email", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return userMapper.mapRow(resultSet);
                }
            }
        } catch (SQLException e) {
            logger.error("can't find user by email", e);
            throw new DaoException("can't find user by email", e);
        }
        return Optional.empty();
    }
}
