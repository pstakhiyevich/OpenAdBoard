package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.Bookmark;

import java.util.List;

/**
 * The Bookmark Data Access Object interface.
 */
public interface BookmarkDao {
    /**
     * Finds a list of bookmarks with the given user id for the current page.
     *
     * @param userId the user's id
     * @param currentPage the current page number
     * @param recordsPerPage the number of bookmarks per page
     * @return the list of user's bookmarks
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<Bookmark> findByUserIdPaginated(long userId, int currentPage, int recordsPerPage) throws DaoException;

    /**
     * Checks whether a bookmark exists
     *
     * @param userId the user's id
     * @param itemId the item's id
     * @return whether a bookmark with the corresponded user id and item id was found
     * @throws DaoException if there is any problem occurred during the data access
     */
    boolean isExist(long userId, long itemId) throws DaoException;

    /**
     * Counts the number of bookmarks with given user id
     *
     * @param userId the user's id
     * @return the number of found entities with given user id
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countByUserId(long userId) throws DaoException;
}
