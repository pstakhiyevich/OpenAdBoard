package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.CategoryDao;
import com.stakhiyevich.openadboard.model.dao.JdbcTemplate;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.mapper.impl.CategoryRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl extends AbstractDao<Category> implements CategoryDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_FIND_ALL = "SELECT id, title " +
            "FROM item_categories " +
            "ORDER BY title ASC";
    private static final String SQL_CREATE = "INSERT INTO item_categories(item_categories.title) VALUES(?)";
    private static final String SQL_UPDATE = "UPDATE item_categories SET item_categories.title = ? WHERE item_categories.id = ? ";
    private static final String SQL_DELETE = "DELETE FROM item_categories WHERE item_categories.id = ? ";
    private static final String SQL_FIND_BY_ID =
            "SELECT item_categories.id, item_categories.title " +
                    "FROM item_categories " +
                    "WHERE item_categories.id = ?";
    private static final String SQL_COUNT_CATEGORIES = "SELECT COUNT(item_categories.id) FROM item_categories";
    private static final String SQL_PAGINATION = " LIMIT ?, ? ";

    private final JdbcTemplate<Category> jdbcTemplate = new JdbcTemplate<>();
    private final CategoryRowMapper categoryRowMapper = new CategoryRowMapper();

    @Override
    public List<Category> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(connection, SQL_FIND_ALL, categoryRowMapper);
        } catch (DaoException e) {
            logger.error("can't find categories", e);
            throw new DaoException("can't find categories", e);
        }
    }

    @Override
    public List<Category> findAllCategoriesPagination(int currentPage, int categoriesPerPage) throws DaoException {
        int startItem = currentPage * categoriesPerPage - categoriesPerPage;
        Object[] args = {startItem, categoriesPerPage};
        try {
            return jdbcTemplate.query(connection, SQL_FIND_ALL + SQL_PAGINATION, args, categoryRowMapper);
        } catch (DaoException e) {
            logger.error("can't find users", e);
            throw new DaoException("can't find users", e);
        }
    }

    @Override
    public int countAllCategories() throws DaoException {
        return jdbcTemplate.query(connection, SQL_COUNT_CATEGORIES);
    }

    @Override
    public Optional<Category> findById(long id) {
        Object[] args = {id};
        List<Category> categories;
        try {
            categories = jdbcTemplate.query(connection, SQL_FIND_BY_ID, args, categoryRowMapper);
            if (!categories.isEmpty()) {
                return Optional.ofNullable(categories.get(0));
            }
        } catch (DaoException e) {
            logger.error("can't find a category with id {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Category category) throws DaoException {
        return delete(category.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        Object[] args = {id};
        try {
            return jdbcTemplate.update(connection, SQL_DELETE, args) >= 0;
        } catch (DaoException e) {
            logger.error("can't delete comment with id {}", id, e);
            throw new DaoException("can't comment item", e);
        }
    }

    @Override
    public boolean create(Category category) throws DaoException {
        Object[] args = {category.getTitle()};
        return jdbcTemplate.update(connection, SQL_CREATE, args) >= 0;
    }

    @Override
    public Optional<Category> update(Category category) throws DaoException {
        Object[] args = {category.getTitle(), category.getId()};
        return jdbcTemplate.update(connection, SQL_UPDATE, args, category);
    }
}
