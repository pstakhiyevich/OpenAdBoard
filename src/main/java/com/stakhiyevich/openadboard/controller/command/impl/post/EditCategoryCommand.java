package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CategoryService;
import com.stakhiyevich.openadboard.service.impl.CategoryServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.AddCategoryFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.CATEGORY_MANAGEMENT_PAGE_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CATEGORY_TITLE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.SELECTED_CATEGORY;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;
import static com.stakhiyevich.openadboard.model.mapper.DatabaseColumnTitleHolder.CATEGORY_ID;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CATEGORY_EDIT_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CATEGORY_EDIT_SUCCESS;

public class EditCategoryCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        CategoryService categoryService = CategoryServiceImpl.getInstance();
        FormValidator validator = AddCategoryFormValidator.getInstance();

        if (!user.getRole().equals(UserRole.MODER) && !user.getRole().equals(UserRole.ADMIN)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        long categoryId = parseLongParameter(request.getParameter(CATEGORY_ID));
        Map<String, String> validationFeedback = validator.validateForm(request.getParameterMap());
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(EDIT_CATEGORY_VALIDATION_FEEDBACK, validationFeedback);
            session.setAttribute(SELECTED_CATEGORY, categoryId);
            return new Router(CATEGORY_MANAGEMENT_PAGE_URL, REDIRECT);
        }

        String categoryTitle = request.getParameter(CATEGORY_TITLE);
        boolean result = categoryService.updateCategory(categoryId, categoryTitle);
        if (result) {
            session.setAttribute(CATEGORY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CATEGORY_EDIT_SUCCESS);
        } else {
            session.setAttribute(CATEGORY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CATEGORY_EDIT_FAIL);
        }
        return new Router(CATEGORY_MANAGEMENT_PAGE_URL, REDIRECT);
    }
}
