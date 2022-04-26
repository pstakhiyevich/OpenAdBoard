package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.EDIT_USER_PAGE;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.USER_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

public class EditUserPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long editedUserId = parseLongParameter(request.getParameter(USER_ID));

        if (user == null || user.getId() != editedUserId) {
            return new Router(ERROR_PAGE_404, FORWARD);
        }

        request.setAttribute(USER, user);

        return new Router(EDIT_USER_PAGE, FORWARD);
    }
}
