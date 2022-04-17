package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.CITY_MANAGEMENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CURRENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.NUMBER_OF_PAGES;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class CityManagementPageCommand implements Command {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_CITIES_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user.getRole().equals(UserRole.USER)) {
            return new Router(HOME_URL, REDIRECT);
        }
        CityService cityService = CityServiceImpl.getInstance();

        int currentPage = DEFAULT_PAGE_NUMBER;
        int citiesPerPage = DEFAULT_CITIES_PER_PAGE;
        int numberOfCities = cityService.countAllCities();
        if (request.getParameter(PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(PAGE));
            citiesPerPage = Integer.parseInt(request.getParameter(CITIES_PER_PAGE));
        }
        int numberOfPages = numberOfCities / citiesPerPage;
        if (numberOfPages % citiesPerPage != 0 && numberOfCities != citiesPerPage && numberOfCities != 0) {
            numberOfPages++;
        }
        List<City> cities = cityService.findAllPaginatedCities(currentPage, citiesPerPage);
        request.setAttribute(CITIES, cities);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(CITIES_PER_PAGE, citiesPerPage);
        return new Router(CITY_MANAGEMENT_PAGE, FORWARD);
    }
}
