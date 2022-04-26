package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.Bookmark;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.BOOKMARK_ITEM_ID;
import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.BOOKMARK_USER_ID;

public class BookmarkRowMapper implements RowMapper<Bookmark> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Bookmark> mapRow(ResultSet resultSet) throws SQLException {
        try {
            Bookmark bookmark = new Bookmark();
            bookmark.setUserId(resultSet.getLong(BOOKMARK_USER_ID));
            bookmark.setItemId(resultSet.getLong(BOOKMARK_ITEM_ID));
            return Optional.of(bookmark);
        } catch (SQLException e) {
            logger.error("failed to fetch bookmarks rows", e);
            return Optional.empty();
        }
    }
}
