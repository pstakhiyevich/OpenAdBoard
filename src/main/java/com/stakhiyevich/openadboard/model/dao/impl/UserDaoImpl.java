package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.JdbcTemplate;
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
    private static final String SQL_CREATE_USER =
            "INSERT INTO users(name, email, password, registration_date, hash, avatar, user_statuses_id, user_roles_id) " +
                    "VALUES(?, ?, ?, ?, ?, ?, " +
                    "(SELECT user_statuses.id FROM user_statuses WHERE title = ?), " +
                    "(SELECT user_roles.id FROM user_roles WHERE title = ?))";
    private static final String SQL_FIND_BY_EMAIL =
            "SELECT users.id, users.name, users.email, users.password, users.registration_date, users.hash, users.avatar, user_statuses.title, user_roles.title " +
                    "FROM users " +
                    "JOIN user_statuses ON users.user_statuses_id = user_statuses.id " +
                    "JOIN user_roles ON users.user_roles_id = user_roles.id " +
                    "WHERE email = ?";
    private static final String SQL_IS_EMAIL_EXIST =
            "SELECT id FROM users WHERE email = ? LIMIT 1";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD =
            "SELECT users.id, users.name, users.email, users.registration_date, users.hash, users.avatar, user_statuses.title, user_roles.title " +
                    "FROM users " +
                    "JOIN user_statuses ON users.user_statuses_id = user_statuses.id " +
                    "JOIN user_roles ON users.user_roles_id = user_roles.id " +
                    "WHERE users.email = ? AND users.password = ?";
    private static final String SQL_COUNT_USERS = "SELECT COUNT(users.id) FROM users";
    private static final String SQL_PAGINATION = " LIMIT ?, ? ";

    private final RowMapper<User> userMapper = new UserRowMapper();
    private final JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();

    @Override
    public List<User> findAll() throws DaoException {
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
    public Optional<User> findById(long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean isEmailExist(String email) throws DaoException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_IS_EMAIL_EXIST)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            logger.error("failed to check if user with {} email exists", email, e);
            throw new DaoException("failed to check if user with " + email + " exists", e);
        }
    }

    @Override
    public boolean createUser(User user, String password, String userHash) throws DaoException {
        Object[] args = {
                user.getName(),
                user.getEmail(),
                password,
                user.getRegistrationDate(),
                userHash,
                user.getAvatar(),
                user.getStatus().name(),
                user.getRole().name()};
        return jdbcTemplate.update(connection, SQL_CREATE_USER, args) >= 0;
    }

    @Override
    public int countAllUsers() throws DaoException {
        return jdbcTemplate.query(connection, SQL_COUNT_USERS);
    }

    @Override
    public List<User> findAllPaginatedUsers(int currentPage, int usersPerPage) throws DaoException {
        int startItem = currentPage * usersPerPage - usersPerPage;
        Object[] args = {startItem, usersPerPage};
        return jdbcTemplate.query(connection, SQL_FIND_ALL_USERS + SQL_PAGINATION, args, userMapper);
    }

    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public Optional<User> update(User user) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
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
