package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.PREVIOUS_COMMAND;

public class UserPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPageCommand = request.getQueryString();
        long requestedUserId = parseLongParameter(request.getParameter(USER_ID));
        UserService userService = UserServiceImpl.getInstance();
        ItemService itemService = ItemServiceImpl.getInstance();
        PageCounter pageCounter = PageCounter.getInstance();

        Optional<User> requestedUser;
        try {
            requestedUser = userService.findUserById(requestedUserId);
        } catch (ServiceException e) {
            logger.error("failed to find a user by id", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        if (requestedUser.isEmpty()) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        int currentPage = parseIntParameter(request.getParameter(PAGE)) != 0 ? parseIntParameter(request.getParameter(PAGE)) : DEFAULT_PAGE_NUMBER;
        int itemsPerPage = parseIntParameter(request.getParameter(ITEMS_PER_PAGE)) != 0 ? parseIntParameter(request.getParameter(ITEMS_PER_PAGE)) :DEFAULT_ITEMS_PER_PAGE;
        int numberOfItems = itemService.countItemsByUserId(requestedUserId);
        int numberOfPages = pageCounter.countNumberOfPages(numberOfItems, itemsPerPage);
        if (currentPage > numberOfPages ) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        List<Item> items;
        try {
            items = itemService.findPaginatedItemsByUserId(requestedUserId, currentPage, itemsPerPage);
        } catch (ServiceException e) {
            logger.error("failed to find items by user id", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        request.setAttribute(ITEMS, items);
        session.setAttribute(PREVIOUS_COMMAND, currentPageCommand);
        request.setAttribute(NUMBER_OF_ITEMS, numberOfItems);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(ITEMS_PER_PAGE, itemsPerPage);
        request.setAttribute(REQUESTED_USER, requestedUser.get());

        return new Router(USER_PAGE, FORWARD);
    }
}