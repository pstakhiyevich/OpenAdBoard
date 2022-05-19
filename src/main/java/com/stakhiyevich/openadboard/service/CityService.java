package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.City;

import java.util.List;

/**
 * The City service interface.
 */
public interface CityService {

    /**
     * Finds all cities.
     *
     * @return a list of cities
     */
    List<City> findAllCities() throws ServiceException;

    /**
     * Finds all cities for the current page.
     *
     * @param currentPage the current page number
     * @param citiesPerPage the cities per page number
     * @return a list of cities
     */
    List<City> findAllPaginatedCities(int currentPage, int citiesPerPage) throws ServiceException;

    /**
     * Counts the number of cities.
     *
     * @return the number of cities
     */
    int countAllCities();

    /**
     * Create a city with the specified title
     *
     * @param cityTitle a city's title
     * @return whether a city was successfully created
     */
    boolean createCity(String cityTitle);

    /**
     * Updates a city with the specified id
     *
     * @param cityId a city's id to update
     * @param cityTitle a new city's title
     * @return whether a city was successfully updated
     */
    boolean updateCity(long cityId, String cityTitle);

    /**
     * Deletes a city with specified id.
     *
     * @param cityId a city's id to delete
     * @return whether a city was successfully deleted
     */
    boolean deleteCity(long cityId);
}
