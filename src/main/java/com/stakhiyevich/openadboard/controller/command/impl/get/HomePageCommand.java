package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.SearchFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.PREVIOUS_COMMAND;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.VALIDATION_FEEDBACK;
import static com.stakhiyevich.openadboard.model.query.QueryParametersHolder.SORT_BY;
import static com.stakhiyevich.openadboard.model.query.QueryParametersHolder.SORT_NEW;

public class HomePageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ItemService itemService = ItemServiceImpl.getInstance();
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        CityService cityService = CityServiceImpl.getInstance();
        FormValidator searchValidator = SearchFormValidator.getInstance();
        PageCounter pageCounter = PageCounter.getInstance();
        String currentPageCommand = request.getQueryString();
        Map<String, String[]> requestParameterMap = new HashMap<>(request.getParameterMap());

        if (request.getParameter(SEARCH_QUERY) != null) {
            Map<String, String> validationFeedback = searchValidator.validateForm(requestParameterMap);
            if (!validationFeedback.isEmpty()) {
                requestParameterMap.remove(SEARCH_QUERY);
                request.setAttribute(VALIDATION_FEEDBACK, validationFeedback);
            } else {
                String searchQuery = request.getParameter(SEARCH_QUERY);
                request.setAttribute(SEARCH_QUERY, searchQuery);
            }
        }

        String sortBy = request.getParameter(SORT_BY) != null ? request.getParameter(SORT_BY) : SORT_NEW;
        long selectedCategoryId = parseLongParameter(request.getParameter(SELECTED_CATEGORY));
        long selectedCityId = parseLongParameter(request.getParameter(SELECTED_CITY));

        int currentPage = parseIntParameter(request.getParameter(PAGE)) != 0 ? parseIntParameter(request.getParameter(PAGE)) : DEFAULT_PAGE_NUMBER;
        int itemsPerPage = parseIntParameter(request.getParameter(ITEMS_PER_PAGE)) != 0 ? parseIntParameter(request.getParameter(ITEMS_PER_PAGE)) : DEFAULT_ITEMS_PER_PAGE;
        int numberOfItems = itemService.countItemsWithParameters(requestParameterMap);
        int numberOfPages = pageCounter.countNumberOfPages(numberOfItems, itemsPerPage);
        if (currentPage > numberOfPages) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        List<Category> categories;
        try {
            categories = categoryService.findAllCategories();
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

        List<Item> items;
        try {
            items = itemService.findItemsWithParameters(requestParameterMap);
        } catch (ServiceException e) {
            logger.error("failed to find items", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        request.setAttribute(ITEMS, items);
        request.setAttribute(SORT_BY, sortBy);
        request.setAttribute(CATEGORIES, categories);
        request.setAttribute(CITIES, cities);
        request.setAttribute(SELECTED_CATEGORY, selectedCategoryId);
        request.setAttribute(SELECTED_CITY, selectedCityId);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(ITEMS_PER_PAGE, itemsPerPage);
        session.setAttribute(PREVIOUS_COMMAND, currentPageCommand);

        return new Router(HOME_PAGE, FORWARD);
    }
}
