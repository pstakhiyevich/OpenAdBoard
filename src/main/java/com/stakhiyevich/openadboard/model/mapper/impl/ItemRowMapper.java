package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.*;

public class ItemRowMapper implements RowMapper<Item> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Item> mapRow(ResultSet resultSet) throws SQLException {
        try {
            Item item = new Item();
            item.setId(resultSet.getLong(ITEM_ID));
            item.setTitle(resultSet.getString(ITEM_TITLE));
            item.setPrice(resultSet.getBigDecimal(ITEM_PRICE));
            item.setDescription(resultSet.getString(ITEM_DESCRIPTION));
            item.setContact(resultSet.getString(ITEM_CONTACT));
            item.setCreateTime(resultSet.getObject(ITEM_CREATE_TIME, LocalDateTime.class));
            item.setUpdateTime(resultSet.getObject(ITEM_UPDATE_TIME, LocalDateTime.class));
            item.setPicture(resultSet.getString(ITEM_PICTURE));
            CategoryRowMapper categoryRowMapper = new CategoryRowMapper();
            Optional<Category> category = categoryRowMapper.mapRow(resultSet);
            category.ifPresent(item::setCategory);
            UserRowMapper userRowMapper = new UserRowMapper();
            Optional<User> user = userRowMapper.mapRow(resultSet);
            user.ifPresent(item::setUser);
            CityRowMapper cityRowMapper = new CityRowMapper();
            Optional<City> city = cityRowMapper.mapRow(resultSet);
            city.ifPresent(item::setCity);
            return Optional.of(item);
        } catch (SQLException e) {
            logger.error("failed to fetch data from the result set");
            return Optional.empty();
        }
    }
}
