package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.City;

import java.util.List;

/**
 * The City Data Access Object interface
 */
public interface CityDao {
    /**
     * Finds a list of cities.
     *
     * @return the list of cities
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<City> findAllCities() throws DaoException;

    /**
     * Finds a list of cities for the current page.
     *
     * @param currentPage the current page number
     * @param citiesPerPage the number of cities per page
     * @return the list of cities
     * @throws DaoException if there is any problem occurred during the data access
     */
    List<City> findAllPaginatedCities(int currentPage, int citiesPerPage) throws DaoException;

    /**
     * Counts the number of cities.
     *
     * @return the number of found cities
     * @throws DaoException if there is any problem occurred during the data access
     */
    int countAllCities() throws DaoException;
}
