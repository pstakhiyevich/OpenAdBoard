package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Bookmark;

import java.util.List;

public interface BookmarkDao {

    List<Bookmark> findByUserIdPaginated(long userId, int currentPage, int recordsPerPage) throws DaoException;

    boolean isExist(long userId, long itemId) throws DaoException;

    int countByUserId(long userId) throws DaoException;
}
