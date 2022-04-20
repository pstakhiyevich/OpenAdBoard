package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.CATEGORY_MANAGEMENT_PAGE_URL;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.CATEGORY_MANAGEMENT_PAGE_FEEDBACK;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.CATEGORY_ID;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CATEGORY_DELETE_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CATEGORY_DELETE_SUCCESS;

public class DeleteCategoryCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        CategoryService categoryService = CategoryServiceImpl.getInstance();

        if (!user.getRole().equals(UserRole.MODER) && !user.getRole().equals(UserRole.ADMIN)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        long categoryId = parseLongParameter(request.getParameter(CATEGORY_ID));

        boolean result = categoryService.deleteCategory(categoryId);
        if (result) {
            session.setAttribute(CATEGORY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CATEGORY_DELETE_SUCCESS);
        } else {
            session.setAttribute(CATEGORY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CATEGORY_DELETE_FAIL);
        }
        return new Router(CATEGORY_MANAGEMENT_PAGE_URL, REDIRECT);
    }
}
