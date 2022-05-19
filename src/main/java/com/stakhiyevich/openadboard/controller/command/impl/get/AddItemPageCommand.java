package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ADD_ITEM_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_500;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CATEGORIES;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CITIES;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;

public class AddItemPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        CityService cityService = CityServiceImpl.getInstance();

        List<Category> itemCategories;
        try {
            itemCategories = categoryService.findAllCategories();
        } catch (ServiceException e) {
            logger.error("failed to find categories", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        List<City> cities;
        try {
            cities = cityService.findAllCities();
        } catch (ServiceException e) {
            logger.error("failed to find cities", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        request.setAttribute(CITIES, cities);
        request.setAttribute(CATEGORIES, itemCategories);

        return new Router(ADD_ITEM_PAGE, FORWARD);
    }
}
