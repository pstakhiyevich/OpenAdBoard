package com.stakhiyevich.openadboard.model.dao.impl;


import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.CommentDao;
import com.stakhiyevich.openadboard.model.dao.CustomJdbcTemplate;
import com.stakhiyevich.openadboard.model.entity.Comment;
import com.stakhiyevich.openadboard.model.mapper.impl.CommentRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CommentDaoImpl extends AbstractDao<Comment> implements CommentDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_ADD_COMMENT = "INSERT INTO comments(comments.text, comments.create_time, comments.items_id, comments.users_id) " +
            "VALUES (?, ?, ?, ?) ";

    private static final String SQL_UPDATE_COMMENT = "UPDATE comments " +
            "SET comments.text = ?, comments.create_time = ?, comments.items_id = ?, comments.users_id = ? " +
            "WHERE comments.id = ?";

    private static final String SQL_DELETE_COMMENT_BY_ID = "DELETE FROM comments WHERE comments.id = ?";

    private static final String SQL_FIND_BY_ID = "SELECT comments.id, comments.text, comments.create_time, comments.items_id, comments.users_id " +
            "FROM comments " +
            "WHERE comments.id = ?";

    private static final String SQL_FIND_BY_ITEM_ID = "SELECT comments.id, comments.text, comments.create_time, comments.items_id, comments.users_id " +
            "FROM comments " +
            "WHERE comments.items_id = ?";

    private static final String SQL_FIND_BY_USER_ID = "SELECT comments.id, comments.text, comments.create_time, comments.items_id, comments.users_id " +
            "FROM comments " +
            "WHERE comments.users_id = ?";

    private final CommentRowMapper commentMapper = new CommentRowMapper();
    private final CustomJdbcTemplate<Comment> customJdbcTemplate = new CustomJdbcTemplate<>();

    @Override
    public Optional<Comment> findById(long id) throws DaoException {
        Object[] args = {id};
        List<Comment> comments;
        try {
            comments = customJdbcTemplate.query(connection, SQL_FIND_BY_ID, args, commentMapper);
            if (!comments.isEmpty()) {
                return Optional.ofNullable(comments.get(0));
            }
        } catch (DaoException e) {
            logger.error("failed to find comment", e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Comment comment) throws DaoException {
        return delete(comment.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        Object[] args = {id};
        try {
            return customJdbcTemplate.update(connection, SQL_DELETE_COMMENT_BY_ID, args) >= 0;
        } catch (DaoException e) {
            logger.error("failed to delete comment with id {}", id, e);
            throw new DaoException("failed to comment item", e);
        }
    }

    @Override
    public boolean create(Comment comment) throws DaoException {
        Object[] args = {
                comment.getText(),
                comment.getCreateTime(),
                comment.getItemId(),
                comment.getUserId()};
        return customJdbcTemplate.update(connection, SQL_ADD_COMMENT, args) >= 0;
    }

    @Override
    public Optional<Comment> update(Comment comment) throws DaoException {
        Object[] args = {
                comment.getText(),
                comment.getCreateTime(),
                comment.getItemId(),
                comment.getUserId()};
        return customJdbcTemplate.update(connection, SQL_UPDATE_COMMENT, args, comment);
    }

    @Override
    public List<Comment> findByItemId(long itemId) throws DaoException {
        Object[] args = {itemId};
        try {
            return customJdbcTemplate.query(connection, SQL_FIND_BY_ITEM_ID, args, commentMapper);
        } catch (DaoException e) {
            logger.error("failed to find comments", e);
            throw new DaoException("failed to find comments", e);
        }
    }

    @Override
    public List<Comment> findByUserId(long userId) throws DaoException {
        Object[] args = {userId};
        try {
            return customJdbcTemplate.query(connection, SQL_FIND_BY_USER_ID, args, commentMapper);
        } catch (DaoException e) {
            logger.error("failed to find comments", e);
            throw new DaoException("failed to find comments", e);
        }
    }
}
