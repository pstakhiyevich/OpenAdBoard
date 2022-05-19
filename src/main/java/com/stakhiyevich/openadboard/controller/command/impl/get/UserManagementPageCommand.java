package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.*;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CURRENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.NUMBER_OF_PAGES;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.*;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class UserManagementPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        UserService userService = UserServiceImpl.getInstance();
        PageCounter pageCounter = PageCounter.getInstance();

        if (!user.getRole().equals(UserRole.ADMIN)) {
            return new Router(HOME_URL, REDIRECT);
        }

        int currentPage = parseIntParameter(request.getParameter(PAGE)) != 0 ? parseIntParameter(request.getParameter(PAGE)) : DEFAULT_PAGE_NUMBER;
        int usersPerPage = parseIntParameter(request.getParameter(USERS_PER_PAGE)) != 0 ? parseIntParameter(request.getParameter(USERS_PER_PAGE)) : DEFAULT_USERS_PER_PAGE;
        int numberOfUsers = userService.countAllUsers();
        int numberOfPages = pageCounter.countNumberOfPages(numberOfUsers, usersPerPage);
        if (currentPage > numberOfPages ) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        List<User> users;
        try {
            users = userService.findAllPaginatedUsers(currentPage, usersPerPage);
        } catch (ServiceException e) {
            logger.error("failed to find users", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(USERS_PER_PAGE, usersPerPage);
        request.setAttribute(USERS, users);

        return new Router(USER_MANAGEMENT_PAGE, FORWARD);
    }
}










