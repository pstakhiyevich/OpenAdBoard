package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.CATEGORY_MANAGEMENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class CategoryManagementPageCommand implements Command {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_CATEGORIES_PER_PAGE = 10;

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        if (user.getRole().equals(UserRole.USER)) {
            return new Router(HOME_URL, REDIRECT);
        }
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        int currentPage = DEFAULT_PAGE_NUMBER;
        int categoriesPerPage = DEFAULT_CATEGORIES_PER_PAGE;
        int numberOfCategories = categoryService.countAllCategories();
        if (request.getParameter(PAGE) != null) {
            currentPage = Integer.parseInt(request.getParameter(PAGE));
            categoriesPerPage = Integer.parseInt(request.getParameter(CATEGORIES_PER_PAGE));
        }
        int numberOfPages = numberOfCategories / categoriesPerPage;
        if (numberOfPages % categoriesPerPage != 0 && numberOfCategories != categoriesPerPage && numberOfCategories != 0) {
            numberOfPages++;
        }
        List<Category> categories = categoryService.findAllPaginatedCategories(currentPage, categoriesPerPage);
        request.setAttribute(CATEGORIES, categories);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(CATEGORIES_PER_PAGE, categoriesPerPage);
        return new Router(CATEGORY_MANAGEMENT_PAGE, FORWARD);
    }
}