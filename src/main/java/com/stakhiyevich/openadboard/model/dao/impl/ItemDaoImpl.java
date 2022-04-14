package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.ItemDao;
import com.stakhiyevich.openadboard.model.dao.CustomJdbcTemplate;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.mapper.impl.ItemRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ItemDaoImpl extends AbstractDao<Item> implements ItemDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_CREATE_ITEM =
            "INSERT INTO items(title, price, description, contact, create_time, update_time, picture, item_categories_id, users_id, cities_id) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    private static final String SQL_UPDATE_ITEM = "UPDATE items " +
            "SET title = ?, price = ?, description = ?, contact = ?, create_time = ?, update_time = ?, picture = ?, item_categories_id = ?, users_id = ?, cities_id = ? " +
            "WHERE items.id = ?";
    private static final String SQL_FIND_ITEMS = "SELECT items.id, items.title, items.price, items.description, items.contact, items.create_time, items.update_time, items.picture, " +
            "item_categories.id, item_categories.title, " +
            "users.id, users.name, users.email, users.password, users.registration_date, users.hash, users.avatar, " +
            "user_statuses.id, user_statuses.title, " +
            "user_roles.id, user_roles.title, " +
            "cities.id, cities.title " +
            "FROM items " +
            "INNER JOIN item_categories ON items.item_categories_id = item_categories.id " +
            "INNER JOIN users ON items.users_id = users.id " +
            "INNER JOIN user_statuses ON users.user_statuses_id = user_statuses.id " +
            "INNER JOIN user_roles ON users.user_roles_id = user_roles.id " +
            "INNER JOIN cities ON items.cities_id = cities.id ";

    private static final String SQL_COUNT_ITEMS = "SELECT COUNT(items.id) FROM items INNER JOIN item_categories ON items.item_categories_id = item_categories.id INNER JOIN cities ON items.cities_id = cities.id";
    private static final String SQL_DELETE_ITEM_BY_ID = "DELETE FROM items WHERE items.id = ?";

    private static final String SQL_PAGINATION = " LIMIT ?, ? ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_USER_ID = " users.id = ? ";
    private static final String SQL_USER_ID_IN_ITEM = " items.users_id = ? ";
    private static final String SQL_ITEM_ID = " items.id = ? ";

    private final CustomJdbcTemplate<Item> customJdbcTemplate = new CustomJdbcTemplate<>();
    private final ItemRowMapper itemRowMapper = new ItemRowMapper();

    @Override
    public List<Item> findAll() throws DaoException {
        return customJdbcTemplate.query(connection, SQL_FIND_ITEMS, itemRowMapper);
    }

    @Override
    public Optional<Item> findById(long id) throws DaoException {
        Object[] args = {id};
        List<Item> items;
        try {
            items = customJdbcTemplate.query(connection, SQL_FIND_ITEMS + SQL_WHERE + SQL_ITEM_ID, args, itemRowMapper);
            if (!items.isEmpty()) {
                return Optional.ofNullable(items.get(0));
            }
        } catch (DaoException e) {
            logger.error("failed to find an item with id {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Item item) {
        return false;
    }

    @Override
    public boolean delete(long id) throws DaoException {
        Object[] args = {id};
        try {
            return customJdbcTemplate.update(connection, SQL_DELETE_ITEM_BY_ID, args) >= 0;
        } catch (DaoException e) {
            logger.error("failed to delete item with id {}", id, e);
            throw new DaoException("failed to delete item", e);
        }
    }

    @Override
    public boolean create(Item item) throws DaoException {
        Object[] args = {
                item.getTitle(),
                item.getPrice(),
                item.getDescription(),
                item.getContact(),
                item.getCreateTime(),
                item.getUpdateTime(),
                item.getPicture(),
                item.getCategory().getId(),
                item.getUser().getId(),
                item.getCity().getId()};
        return customJdbcTemplate.update(connection, SQL_CREATE_ITEM, args) >= 0;
    }

    @Override
    public int countItems() throws DaoException {
        return customJdbcTemplate.query(connection, SQL_COUNT_ITEMS);
    }

    @Override
    public int countItemsByUserId(long userId) throws DaoException {
        Object[] args = {userId};
        return customJdbcTemplate.query(connection, SQL_COUNT_ITEMS + SQL_WHERE + SQL_USER_ID_IN_ITEM, args);
    }

    @Override
    public List<Item> findItems(int currentPage, int recordsPerPage) throws DaoException {
        int startItem = currentPage * recordsPerPage - recordsPerPage;
        Object[] args = {startItem, recordsPerPage};
        try {
            return customJdbcTemplate.query(connection, SQL_FIND_ITEMS + SQL_PAGINATION, args, itemRowMapper);
        } catch (DaoException e) {
            logger.error("failed to find items", e);
            throw new DaoException("failed to find items", e);
        }
    }

    @Override
    public List<Item> findItemsByUserId(long userId, int currentPage, int recordsPerPage) throws DaoException {
        int startItem = currentPage * recordsPerPage - recordsPerPage;
        Object[] args = {userId, startItem, recordsPerPage};
        try {
            return customJdbcTemplate.query(connection, SQL_FIND_ITEMS + SQL_WHERE + SQL_USER_ID + SQL_PAGINATION, args, itemRowMapper);
        } catch (DaoException e) {
            logger.error("failed to find items", e);
            throw new DaoException("failed to find items", e);
        }
    }

    @Override
    public Optional<Item> update(Item item) throws DaoException {
        Object[] args = {
                item.getTitle(),
                item.getPrice(),
                item.getDescription(),
                item.getContact(),
                item.getCreateTime(),
                item.getUpdateTime(),
                item.getPicture(),
                item.getCategory().getId(),
                item.getUser().getId(),
                item.getCity().getId(),
                item.getId()};
        return customJdbcTemplate.update(connection, SQL_UPDATE_ITEM, args, item);
    }
}
