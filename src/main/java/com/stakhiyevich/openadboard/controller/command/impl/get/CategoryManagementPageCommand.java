package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.CATEGORY_MANAGEMENT_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.*;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class CategoryManagementPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        PageCounter pageCounter = PageCounter.getInstance();
        CategoryService categoryService = CategoryServiceImpl.getInstance();

        if (!user.getRole().equals(UserRole.MODER) && !user.getRole().equals(UserRole.ADMIN)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        int currentPage = parseIntParameter(request.getParameter(PAGE)) != 0 ? parseIntParameter(request.getParameter(PAGE)) : DEFAULT_PAGE_NUMBER;
        int categoriesPerPage = parseIntParameter(request.getParameter(CATEGORIES_PER_PAGE)) != 0 ? parseIntParameter(request.getParameter(CATEGORIES_PER_PAGE)) : DEFAULT_CATEGORIES_PER_PAGE;
        int numberOfCategories = categoryService.countAllCategories();
        int numberOfPages = pageCounter.countNumberOfPages(numberOfCategories, categoriesPerPage);
        if (currentPage > numberOfPages) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        List<Category> categories = categoryService.findAllPaginatedCategories(currentPage, categoriesPerPage);

        request.setAttribute(CATEGORIES, categories);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(CATEGORIES_PER_PAGE, categoriesPerPage);

        return new Router(CATEGORY_MANAGEMENT_PAGE, FORWARD);
    }
}
