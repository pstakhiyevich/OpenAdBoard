package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.BookmarkDaoImpl;
import com.stakhiyevich.openadboard.model.dao.impl.ItemDaoImpl;
import com.stakhiyevich.openadboard.model.entity.Bookmark;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.dto.BookmarkEntityDto;
import com.stakhiyevich.openadboard.service.BookmarkService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookmarkServiceImpl implements BookmarkService {

    private static final Logger logger = LogManager.getLogger();
    private static BookmarkService instance;

    private BookmarkServiceImpl() {
    }

    public static BookmarkService getInstance() {
        if (instance == null) {
            instance = new BookmarkServiceImpl();
        }
        return instance;
    }

    @Override
    public List<BookmarkEntityDto> findByUserId(long userId, int currentPage, int recordsPerPage) {
        AbstractDao bookmarkDao = new BookmarkDaoImpl();
        AbstractDao itemDao = new ItemDaoImpl();
        List<BookmarkEntityDto> bookmarksDto = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(bookmarkDao, itemDao);
            try {
                List<Bookmark> bookmarks = ((BookmarkDaoImpl) bookmarkDao).findByUserIdPaginated(userId, currentPage, recordsPerPage);
                for (Bookmark bookmark : bookmarks) {
                    Optional<Item> item = itemDao.findById(bookmark.getItemId());
                    item.ifPresent(itemEntity -> bookmarksDto.add(createBookmarkEntityDtoObject(userId, itemEntity)));
                }
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return bookmarksDto;
    }

    @Override
    public boolean addBookmark(long userId, long itemId) {
        AbstractDao bookmarkDao = new BookmarkDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(bookmarkDao);
            try {
                Bookmark bookmark = new Bookmark();
                bookmark.setUserId(userId);
                bookmark.setItemId(itemId);
                boolean result = bookmarkDao.create(bookmark);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean deleteBookmark(long userId, long itemId) {
        AbstractDao bookmarkDao = new BookmarkDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(bookmarkDao);
            try {
                Bookmark bookmark = new Bookmark();
                bookmark.setUserId(userId);
                bookmark.setItemId(itemId);
                boolean result = bookmarkDao.delete(bookmark);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean isExist(long userId, long itemId) {
        AbstractDao bookmarkDao = new BookmarkDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(bookmarkDao);
            try {
                boolean result = ((BookmarkDaoImpl) bookmarkDao).isExist(userId, itemId);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return false;
    }

    @Override
    public int countByUserId(long userId) {
        AbstractDao bookmarkDao = new BookmarkDaoImpl();
        int numberOfItems = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(bookmarkDao);
            try {
                numberOfItems = ((BookmarkDaoImpl) bookmarkDao).countByUserId(userId);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return numberOfItems;
    }

    private BookmarkEntityDto createBookmarkEntityDtoObject(long userId, Item item) {
        BookmarkEntityDto bookmark = new BookmarkEntityDto();
        bookmark.setUserId(userId);
        bookmark.setItemId(item.getId());
        bookmark.setItemTitle(item.getTitle());
        bookmark.setItemPrice(item.getPrice());
        bookmark.setItemDescription(item.getDescription());
        bookmark.setItemPicture(item.getPicture());
        bookmark.setItemCity(item.getCity().getTitle());
        return bookmark;
    }
}
