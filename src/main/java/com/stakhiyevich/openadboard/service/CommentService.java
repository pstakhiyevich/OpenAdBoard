package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.dto.CommentEntityDto;

import java.util.List;

public interface CommentService {

    boolean addComment(String text, long itemId, long userId);

    boolean deleteComment(long id);

    List<CommentEntityDto> findByItemId(long itemId);

    List<CommentEntityDto> findByUserId(long userId);

    boolean userCanDeleteComment(long userId, long commentId);
}
