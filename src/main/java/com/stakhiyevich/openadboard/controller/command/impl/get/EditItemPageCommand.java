package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.City;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

public class EditItemPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        ItemService itemService = ItemServiceImpl.getInstance();
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        CityService cityService = CityServiceImpl.getInstance();
        int itemId = parseIntParameter(request.getParameter(ITEM_ID));

        if (!itemService.canUserEditItem(user.getId(), itemId)) {
            return new Router(ERROR_PAGE_404, FORWARD);
        }

        Item item;
        try {
            item = itemService.findItemById(itemId).get();
        } catch (ServiceException e) {
            logger.error("failed to find an item by id", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

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
        request.setAttribute(ITEM, item);

        return new Router(EDIT_ITEM_PAGE, FORWARD);
    }
}
