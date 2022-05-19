package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.Category;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.*;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.*;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class CategoryManagementPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

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

        List<Category> categories;
        try {
            categories = categoryService.findAllPaginatedCategories(currentPage, categoriesPerPage);
        } catch (ServiceException e) {
            logger.error("failed to find categories", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        request.setAttribute(CATEGORIES, categories);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(CATEGORIES_PER_PAGE, categoriesPerPage);

        return new Router(CATEGORY_MANAGEMENT_PAGE, FORWARD);
    }
}
