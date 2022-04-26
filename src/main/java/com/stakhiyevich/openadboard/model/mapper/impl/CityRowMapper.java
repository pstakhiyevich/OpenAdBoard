package com.stakhiyevich.openadboard.model.mapper.impl;

import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.CITY_ID;
import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.CITY_TITLE;

public class CityRowMapper implements RowMapper<City> {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<City> mapRow(ResultSet resultSet) throws SQLException {
        try {
            City city = new City();
            city.setId(resultSet.getLong(CITY_ID));
            city.setTitle(resultSet.getString(CITY_TITLE));
            return Optional.of(city);
        } catch (SQLException e) {
            logger.error("failed to fetch city rows", e);
            return Optional.empty();
        }
    }
}
