package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.dto.BookmarkEntityDto;

import java.util.List;

public interface BookmarkService {

    List<BookmarkEntityDto> findByUserId(long userId, int currentPage, int recordsPerPage);

    boolean addBookmark(long userId, long itemId);

    boolean deleteBookmark(long userId, long itemId);

    boolean isExist(long userId, long itemId);

    int countByUserId(long userId);
}
