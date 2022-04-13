package com.stakhiyevich.openadboard.service.impl;

import com.stakhiyevich.openadboard.exception.DaoException;
import com.stakhiyevich.openadboard.exception.TransactionException;
import com.stakhiyevich.openadboard.model.dao.AbstractDao;
import com.stakhiyevich.openadboard.model.dao.TransactionManager;
import com.stakhiyevich.openadboard.model.dao.impl.CityDaoImpl;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.service.CityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityServiceImpl implements CityService {

    private static final Logger logger = LogManager.getLogger();
    private static CityService instance;

    private CityServiceImpl() {
    }

    public static CityService getInstance() {
        if (instance == null) {
            instance = new CityServiceImpl();
        }
        return instance;
    }

    @Override
    public List<City> findAllCities() {
        AbstractDao cityDao = new CityDaoImpl();
        List<City> cities = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(cityDao);
            try {
                cities = ((CityDaoImpl) cityDao).findAll();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return cities;
    }

    @Override
    public List<City> findAllPaginatedCities(int currentPage, int citiesPerPage) {
        AbstractDao cityDao = new CityDaoImpl();
        List<City> cities = new ArrayList<>();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(cityDao);
            try {
                cities = ((CityDaoImpl) cityDao).findAllCitiesPagination(currentPage, citiesPerPage);
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return cities;
    }

    @Override
    public int countAllCities() {
        AbstractDao cityDao = new CityDaoImpl();
        int numberOfCities = 0;
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(cityDao);
            try {
                numberOfCities = ((CityDaoImpl) cityDao).countAllCities();
                transactionManager.commit();
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return numberOfCities;
    }


    @Override
    public boolean createCity(String cityTitle) {
        AbstractDao cityDao = new CityDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(cityDao);
            try {
                City city = createCityObject(cityTitle);
                boolean result = cityDao.create(city);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean updateCity(long cityId, String cityTitle) {
        AbstractDao cityDao = new CityDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(cityDao);
            try {
                Optional<City> cityForUpdate = cityDao.findById(cityId);
                City updatedCity = updateCityObject(cityForUpdate.get(), cityTitle);
                Optional<City> result = cityDao.update(updatedCity);
                if (result.isPresent()) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    @Override
    public boolean deleteCity(long cityId) {
        AbstractDao cityDao = new CityDaoImpl();
        try (TransactionManager transactionManager = new TransactionManager()) {
            transactionManager.beginTransaction(cityDao);
            try {
                boolean result = cityDao.delete(cityId);
                if (result) {
                    transactionManager.commit();
                    return true;
                }
            } catch (DaoException e) {
                transactionManager.rollback();
            }
        } catch (TransactionException e) {
            logger.error("can't perform a transaction", e);
        }
        return false;
    }

    private City createCityObject(String cityTitle) {
        City city = new City();
        city.setTitle(cityTitle);
        return city;
    }

    private City updateCityObject(City cityForUpdate, String cityTitle) {
        cityForUpdate.setTitle(cityTitle);
        return cityForUpdate;
    }
}
