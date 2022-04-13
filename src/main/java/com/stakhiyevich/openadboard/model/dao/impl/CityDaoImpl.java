package com.stakhiyevich.openadboard.model.dao.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.CityDao;
import com.stakhiyevich.openadboard.model.dao.JdbcTemplate;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.mapper.impl.CityRowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CityDaoImpl extends AbstractDao<City> implements CityDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String SQL_FIND_ALL = "SELECT id, title " +
            "FROM cities " +
            "ORDER BY title ASC";
    private static final String SQL_FIND_BY_ID =
            "SELECT cities.id, cities.title " +
                    "FROM cities " +
                    "WHERE cities.id = ?";
    private static final String SQL_CREATE = "INSERT INTO cities(cities.title) VALUES ( ? )";
    private static final String SQL_UPDATE = "UPDATE cities SET cities.title = ? WHERE cities.id = ? ";
    private static final String SQL_DELETE = "DELETE FROM cities WHERE cities.id = ? ";
    private static final String SQL_COUNT_CITIES = "SELECT COUNT(cities.id) FROM cities";
    private static final String SQL_PAGINATION = " LIMIT ?, ? ";

    private final JdbcTemplate<City> jdbcTemplate = new JdbcTemplate<>();
    private final CityRowMapper cityRowMapper = new CityRowMapper();

    @Override
    public List<City> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(connection, SQL_FIND_ALL, cityRowMapper);
        } catch (DaoException e) {
            logger.error("can't find cities", e);
            throw new DaoException("can't find cities", e);
        }
    }

    @Override
    public List<City> findAllCitiesPagination(int currentPage, int citiesPerPage) throws DaoException {
        int startItem = currentPage * citiesPerPage - citiesPerPage;
        Object[] args = {startItem, citiesPerPage};
        try {
            return jdbcTemplate.query(connection, SQL_FIND_ALL + SQL_PAGINATION, args, cityRowMapper);
        } catch (DaoException e) {
            logger.error("can't find users", e);
            throw new DaoException("can't find users", e);
        }
    }

    @Override
    public int countAllCities() throws DaoException {
        return jdbcTemplate.query(connection, SQL_COUNT_CITIES);
    }

    @Override
    public Optional<City> findById(long id) {
        Object[] args = {id};
        List<City> cities;
        try {
            cities = jdbcTemplate.query(connection, SQL_FIND_BY_ID, args, cityRowMapper);
            if (!cities.isEmpty()) {
                return Optional.ofNullable(cities.get(0));
            }
        } catch (DaoException e) {
            logger.error("can't find a city with id {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(City city) throws DaoException {
        return delete(city.getId());
    }

    @Override
    public boolean delete(long id) throws DaoException {
        Object[] args = {id};
        try {
            return jdbcTemplate.update(connection, SQL_DELETE, args) >= 0;
        } catch (DaoException e) {
            logger.error("can't delete comment with id {}", id, e);
            throw new DaoException("can't comment item", e);
        }
    }

    @Override
    public boolean create(City city) throws DaoException {
        Object[] args = {city.getTitle()};
        return jdbcTemplate.update(connection, SQL_CREATE, args) >= 0;
    }

    @Override
    public Optional<City> update(City city) throws DaoException {
        Object[] args = {city.getTitle(), city.getId()};
        return jdbcTemplate.update(connection, SQL_UPDATE, args, city);
    }
}
