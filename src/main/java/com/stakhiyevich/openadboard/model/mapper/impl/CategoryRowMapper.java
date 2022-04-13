package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.CATEGORY_ID;
import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.CATEGORY_TITLE;

public class CategoryRowMapper implements RowMapper<Category> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Category> mapRow(ResultSet resultSet) throws SQLException {

        try {
            Category category = new Category();
            category.setId(resultSet.getLong(CATEGORY_ID));
            category.setTitle(resultSet.getString(CATEGORY_TITLE));
            return Optional.of(category);
        } catch (SQLException e) {
            logger.error("failed to fetch category rows", e);
            return Optional.empty();
        }
    }
}
