package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Comment;

import java.util.List;

public interface CommentDao {

    List<Comment> findByItemId(long itemId) throws DaoException;

    List<Comment> findByUserId(long userId) throws DaoException;

}
