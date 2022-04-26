package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.USER_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.PREVIOUS_COMMAND;

public class UserPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPageCommand = request.getQueryString();
        long requestedUserId = parseLongParameter(request.getParameter(USER_ID));
        UserService userService = UserServiceImpl.getInstance();
        ItemService itemService = ItemServiceImpl.getInstance();
        PageCounter pageCounter = PageCounter.getInstance();

        Optional<User> requestedUser = userService.findUserById(requestedUserId);
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

        List<Item> items = itemService.findPaginatedItemsByUserId(requestedUserId, currentPage, itemsPerPage);

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