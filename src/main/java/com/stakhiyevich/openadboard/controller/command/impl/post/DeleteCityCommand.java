package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CityService;
import com.stakhiyevich.openadboard.service.impl.CityServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.CITY_MANAGEMENT_PAGE_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.CITY_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.CITY_MANAGEMENT_PAGE_FEEDBACK;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CITY_DELETE_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_CITY_DELETE_SUCCESS;

public class DeleteCityCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);

        if (!user.getRole().equals(UserRole.MODER) && !user.getRole().equals(UserRole.ADMIN)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        long cityId = parseLongParameter(request.getParameter(CITY_ID));
        CityService cityService = CityServiceImpl.getInstance();

        boolean result = cityService.deleteCity(cityId);
        if (result) {
            session.setAttribute(CITY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CITY_DELETE_SUCCESS);
        } else {
            session.setAttribute(CITY_MANAGEMENT_PAGE_FEEDBACK, MESSAGE_CITY_DELETE_FAIL);
        }
        return new Router(CITY_MANAGEMENT_PAGE_URL, REDIRECT);
    }
}
