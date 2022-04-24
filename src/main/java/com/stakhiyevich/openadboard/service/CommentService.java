package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.dto.CommentEntityDto;

import java.util.List;

/**
 * The Comment service interface.
 */
public interface CommentService {
    /**
     * Adds a comment with specified text
     *
     * @param text a comment's text
     * @param itemId an item's id
     * @param userId a user's id
     * @return whether a comment was added successfully
     */
    boolean addComment(String text, long itemId, long userId);

    /**
     * Deletes a comment with specified id.
     *
     * @param id a comment's id
     * @return whether a comment was deleted successfully
     */
    boolean deleteComment(long id);

    /**
     * Finds comments with the specified item id.
     *
     * @param itemId an item's id
     * @return a list of comments
     */
    List<CommentEntityDto> findByItemId(long itemId);

    /**
     * Finds comments with the specified user id.
     *
     * @param userId a user's id
     * @return a list of comments
     */
    List<CommentEntityDto> findByUserId(long userId);

    /**
     * Checks whether a user has the rights to delete s comment, in other words whether it is the user's own comment.
     *
     * @param userId a user's id
     * @param commentId a comment's id
     * @return whether a user has the right to delete comment
     */
    boolean userCanDeleteComment(long userId, long commentId);
}