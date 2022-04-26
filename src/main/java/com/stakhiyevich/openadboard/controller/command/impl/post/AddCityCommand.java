package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.AddCityFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.CITY_MANAGEMENT_PAGE_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CITY_TITLE;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CITY_ADD_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CITY_ADD_SUCCESS;

public class AddCityCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        CityService cityService = CityServiceImpl.getInstance();
        FormValidator validator = AddCityFormValidator.getInstance();

        if (!user.getRole().equals(UserRole.MODER) && !user.getRole().equals(UserRole.ADMIN)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        Map<String, String> validationFeedback = validator.validateForm(request.getParameterMap());
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(VALIDATION_FEEDBACK, validationFeedback);
            return new Router(CITY_MANAGEMENT_PAGE_URL, REDIRECT);
        }

        String cityTitle = request.getParameter(CITY_TITLE);
        boolean result = cityService.createCity(cityTitle);
        if (result) {
            session.setAttribute(CITY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CITY_ADD_SUCCESS);
        } else {
            session.setAttribute(CITY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CITY_ADD_FAIL);
        }
        return new Router(CITY_MANAGEMENT_PAGE_URL, REDIRECT);
    }
}
