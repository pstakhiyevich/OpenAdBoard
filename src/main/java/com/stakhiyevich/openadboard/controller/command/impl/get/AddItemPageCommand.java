package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ADD_ITEM_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CATEGORIES;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CITIES;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;

public class AddItemPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        CityService cityService = CityServiceImpl.getInstance();

        List<Category> itemCategories = categoryService.findAllCategories();
        List<City> cities = cityService.findAllCities();

        request.setAttribute(CITIES, cities);
        request.setAttribute(CATEGORIES, itemCategories);

        return new Router(ADD_ITEM_PAGE, FORWARD);
    }
}
