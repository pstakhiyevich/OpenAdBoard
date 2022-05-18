package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.CommentDaoImpl;
import com.stakhiyevich.openadboard.model.dao.impl.UserDaoImpl;
import com.stakhiyevich.openadboard.model.entity.Comment;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.dto.CommentEntityDto;
import com.stakhiyevich.openadboard.service.CommentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LogManager.getLogger();
    private static CommentService instance;

    private CommentServiceImpl() {
    }

    public static CommentService getInstance() {
        if (instance == null) {
            instance = new CommentServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean addComment(String text, long itemId, long userId) {
        AbstractDao commentDao = new CommentDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(commentDao);
            try {
                Comment comment = createCommentEntityObjet(text, itemId, userId);
                boolean isCommentAdded = commentDao.create(comment);
                if (isCommentAdded) {
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
    public boolean deleteComment(long id) {
        AbstractDao commentDao = new CommentDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(commentDao);
            try {
                boolean isCommentDeleted = commentDao.delete(id);
                if (isCommentDeleted) {
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
    public List<CommentEntityDto> findByItemId(long itemId) {
        AbstractDao commentDao = new CommentDaoImpl();
        AbstractDao userDao = new UserDaoImpl();
        List<CommentEntityDto> commentsDto = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(commentDao, userDao);
            try {
                List<Comment> comments = ((CommentDaoImpl) commentDao).findByItemId(itemId);
                for (Comment comment : comments) {
                    Optional<User> user = userDao.findById(comment.getUserId());
                    user.ifPresent(userEntity -> commentsDto.add(createCommentEntityDtoObject(comment, userEntity)));
                }
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return commentsDto;
    }

    @Override
    public List<CommentEntityDto> findByUserId(long userId) {
        AbstractDao commentDao = new CommentDaoImpl();
        AbstractDao userDao = new UserDaoImpl();
        List<CommentEntityDto> commentsDto = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(commentDao, userDao);
            try {
                List<Comment> comments = ((CommentDaoImpl) commentDao).findByUserId(userId);
                for (Comment comment : comments) {
                    Optional<User> user = userDao.findById(comment.getUserId());
                    user.ifPresent(userEntity -> commentsDto.add(createCommentEntityDtoObject(comment, userEntity)));
                }
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return commentsDto;
    }

    @Override
    public boolean userCanDeleteComment(long userId, long commentId) {
        AbstractDao commentDao = new CommentDaoImpl();
        boolean result = false;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(commentDao);
            try {
                long currentUserId = ((CommentDaoImpl) commentDao).findById(commentId).get().getUserId();
                if (currentUserId == userId) {
                    result = true;
                }
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("failed to perform a transaction", e);
        }
        return result;
    }

    private Comment createCommentEntityObjet(String text, long itemId, long userId) {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setCreateTime(LocalDateTime.now());
        comment.setItemId(itemId);
        comment.setUserId(userId);
        return comment;
    }

    private CommentEntityDto createCommentEntityDtoObject(Comment comment, User user) {
        CommentEntityDto commentEntityDto = new CommentEntityDto();
        commentEntityDto.setCommentId(comment.getId());
        commentEntityDto.setItemId(comment.getItemId());
        commentEntityDto.setUserId(comment.getUserId());
        commentEntityDto.setText(comment.getText());
        commentEntityDto.setCreateTime(comment.getCreateTime());
        commentEntityDto.setUserName(user.getName());
        commentEntityDto.setUserAvatar(user.getAvatar());
        commentEntityDto.setUserRole(user.getRole().toString());
        return commentEntityDto;
    }
}
