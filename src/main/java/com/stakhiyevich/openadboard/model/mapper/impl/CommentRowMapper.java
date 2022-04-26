package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.Comment;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.*;

public class CommentRowMapper implements RowMapper<Comment> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Comment> mapRow(ResultSet resultSet) throws SQLException {
        try {
            Comment comment = new Comment();
            comment.setId(resultSet.getLong(COMMENT_ID));
            comment.setText(resultSet.getString(COMMENT_TEXT));
            comment.setCreateTime(resultSet.getObject(COMMENT_CREATE_TIME, LocalDateTime.class));
            comment.setItemId(resultSet.getLong(COMMENT_ITEM_ID));
            comment.setUserId(resultSet.getLong(COMMENT_USER_ID));
            return Optional.of(comment);
        } catch (SQLException e) {
            logger.error("failed to fetch comment rows", e);
            return Optional.empty();
        }
    }
}