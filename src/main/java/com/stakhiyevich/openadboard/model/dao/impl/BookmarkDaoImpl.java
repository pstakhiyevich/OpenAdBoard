package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.BookmarkDao;
import com.stakhiyevich.openadboard.model.dao.CustomJdbcTemplate;
import com.stakhiyevich.openadboard.model.entity.Bookmark;
import com.stakhiyevich.openadboard.model.mapper.impl.BookmarkRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BookmarkDaoImpl extends AbstractDao<Bookmark> implements BookmarkDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_FIND_BY_USER_ID = """
            SELECT bookmarks.users_id, bookmarks.items_id
            FROM bookmarks
            WHERE bookmarks.users_id = ?
            LIMIT ?, ?""";
    private static final String SQL_COUNT_BY_USER_ID = """
            SELECT COUNT(bookmarks.items_id)
            FROM bookmarks
            WHERE bookmarks.users_id = ?""";
    private static final String SQL_ADD_BOOKMARK = """
            INSERT INTO bookmarks (users_id, items_id)
            VALUES (?, ?)""";
    private static final String SQL_DELETE_BOOKMARK = """
            DELETE FROM bookmarks
            WHERE bookmarks.users_id = ? AND bookmarks.items_id = ?""";
    private static final String SQL_FIND_BY_USER_ITEM_ID = """
            SELECT bookmarks.users_id, bookmarks.items_id
            FROM bookmarks
            WHERE bookmarks.users_id = ? AND bookmarks.items_id = ?""";

    private final CustomJdbcTemplate<Bookmark> customJdbcTemplate = new CustomJdbcTemplate<>();
    private final BookmarkRowMapper bookmarkRowMapper = new BookmarkRowMapper();

    @Override
    public Optional<Bookmark> findById(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Bookmark bookmark) throws DaoException {
        Object[] args = {bookmark.getUserId(), bookmark.getItemId()};
        try {
            return customJdbcTemplate.update(connection, SQL_DELETE_BOOKMARK, args) >= 0;
        } catch (DaoException e) {
            logger.error("failed to delete bookmark with user id {} item id {}", bookmark.getUserId(), bookmark.getItemId(), e);
            throw new DaoException("failed to delete bookmark", e);
        }
    }

    @Override
    public boolean delete(long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean create(Bookmark bookmark) throws DaoException {
        Object[] args = {bookmark.getUserId(), bookmark.getItemId()};
        return customJdbcTemplate.update(connection, SQL_ADD_BOOKMARK, args) >= 0;
    }

    @Override
    public Optional<Bookmark> update(Bookmark bookmark) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Bookmark> findByUserIdPaginated(long userId, int currentPage, int recordsPerPage) throws DaoException {
        int startItem = currentPage * recordsPerPage - recordsPerPage;
        Object[] args = {userId, startItem, recordsPerPage};
        try {
            return customJdbcTemplate.query(connection, SQL_FIND_BY_USER_ID, args, bookmarkRowMapper);
        } catch (DaoException e) {
            logger.error("failed to find bookmarks", e);
            throw new DaoException("failed to find bookmarks", e);
        }
    }

    @Override
    public boolean isExist(long userId, long itemId) throws DaoException {
        Object[] args = {userId, itemId};
        try {
            return customJdbcTemplate.query(connection, SQL_FIND_BY_USER_ITEM_ID, args, bookmarkRowMapper).size() > 0;
        } catch (DaoException e) {
            logger.error("failed to find bookmarks", e);
            throw new DaoException("failed to find bookmarks", e);
        }
    }

    @Override
    public int countByUserId(long userId) throws DaoException {
        Object[] args = {userId};
        return customJdbcTemplate.query(connection, SQL_COUNT_BY_USER_ID, args);
    }
}
