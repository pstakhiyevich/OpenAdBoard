package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Comment;

import java.util.List;

/**
 * The Comment Data Access Object interface
 */
public interface CommentDao {
    /**
     * Finds all comments with the specified item id.
     *
     * @param itemId the item's id
     * @return the list of comments
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Comment> findByItemId(long itemId) throws DaoException;

    /**
     * Finds all comments with the specified user id.
     *
     * @param userId the user's id
     * @return the list of comments
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Comment> findByUserId(long userId) throws DaoException;
}
