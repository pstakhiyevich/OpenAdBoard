package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.dto.BookmarkEntityDto;

import java.util.List;

/**
 * The Bookmark service interface.
 */
public interface BookmarkService {
    /**
     * Finds bookmarks with the specified user id for the current page.
     *
     * @param userId the user's id
     * @param currentPage the current page number
     * @param recordsPerPage the bookmarks per page number
     * @return a list of bookmark's data transfer objects
     */
    List<BookmarkEntityDto> findByUserId(long userId, int currentPage, int recordsPerPage);

    /**
     * Adds a new bookmark with the specified user id and item id.
     *
     * @param userId the user's id
     * @param itemId the item's id
     * @return whether a bookmark was successfully created.
     */
    boolean addBookmark(long userId, long itemId);

    /**
     * Deletes a new bookmark with the specified user id and item id.
     *
     * @param userId the user's id
     * @param itemId the item's id
     * @return whether a bookmark was successfully deleted.
     */
    boolean deleteBookmark(long userId, long itemId);

    /**
     * Checks whether a bookmark with the specified user id and item id exists.
     *
     * @param userId the user's id
     * @param itemId the item's id
     * @return whether a bookmark exists.
     */
    boolean isExist(long userId, long itemId);

    /**
     * Counts all bookmarks with the specified user id.
     *
     * @param userId user's id.
     * @return the number of bookmarks.
     */
    int countByUserId(long userId);
}
