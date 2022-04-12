package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.USER_MANAGEMENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class UserManagementPageCommand implements Command {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_USERS_PER_PAGE = 2;

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (!user.getRole().equals(UserRole.ADMIN)) {
            return new Router(HOME_URL, REDIRECT);
        }
        UserService userService = UserServiceImpl.getInstance();
        int currentPage = DEFAULT_PAGE_NUMBER;
        int usersPerPage = DEFAULT_USERS_PER_PAGE;
        int numberOfUsers = userService.countAllUsers();
        if (request.getParameter(PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(PAGE));
            usersPerPage = Integer.parseInt(request.getParameter(USERS_PER_PAGE));
        }
        int numberOfPages = numberOfUsers / usersPerPage;
        if (numberOfPages % usersPerPage != 0 && numberOfUsers != usersPerPage && numberOfUsers != 0) {
            numberOfPages++;
        }
        List<User> users = userService.findAllPaginatedUsers(currentPage, usersPerPage);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(USERS_PER_PAGE, usersPerPage);
        request.setAttribute(USERS, users);
        return new Router(USER_MANAGEMENT_PAGE, FORWARD);
    }
}










