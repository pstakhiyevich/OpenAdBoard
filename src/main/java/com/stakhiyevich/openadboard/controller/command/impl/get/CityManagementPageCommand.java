package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.CITY_MANAGEMENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

public class CityManagementPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        PageCounter pageCounter = PageCounter.getInstance();
        CityService cityService = CityServiceImpl.getInstance();

        if (!user.getRole().equals(UserRole.MODER) && !user.getRole().equals(UserRole.ADMIN)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        int currentPage = parseIntParameter(request.getParameter(PAGE)) != 0 ? parseIntParameter(request.getParameter(PAGE)) : DEFAULT_PAGE_NUMBER;
        int citiesPerPage = parseIntParameter(request.getParameter(CITIES_PER_PAGE)) != 0 ? parseIntParameter(request.getParameter(CITIES_PER_PAGE)) : DEFAULT_CITIES_PER_PAGE;
        int numberOfCities = cityService.countAllCities();
        int numberOfPages = pageCounter.countNumberOfPages(numberOfCities, citiesPerPage);
        if (currentPage > numberOfPages) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        List<City> cities = cityService.findAllPaginatedCities(currentPage, citiesPerPage);

        request.setAttribute(CITIES, cities);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(CITIES_PER_PAGE, citiesPerPage);

        return new Router(CITY_MANAGEMENT_PAGE, FORWARD);
    }
}
