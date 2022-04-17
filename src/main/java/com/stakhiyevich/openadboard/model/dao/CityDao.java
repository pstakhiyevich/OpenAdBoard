package com.stakhiyevich.openadboard.model.dao;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.model.entity.City;

import java.util.List;

public interface CityDao {

    List<City> findAllCities() throws DaoException;

    List<City> findAllPaginatedCities(int currentPage, int citiesPerPage) throws DaoException;

    int countAllCities() throws DaoException;
}
