package com.stakhiyevich.openadboard.service;

import com.stakhiyevich.openadboard.model.entity.City;

import java.util.List;

public interface CityService {

    List<City> findAllCities();

    List<City> findAllPaginatedCities(int currentPage, int citiesPerPage);

    int countAllCities();

    boolean createCity(String cityTitle);

    boolean updateCity(long cityId, String cityTitle);

    boolean deleteCity(long cityId);
}
