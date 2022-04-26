package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ACTIVATE_USER_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.TOKEN;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.ACTIVATION_FEEDBACK;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_FAIL_ACTIVATION;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_SUCCESS_ACTIVATION;

public class ActivateUserCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String activationToken = request.getParameter(TOKEN);
        UserService userService = UserServiceImpl.getInstance();

        boolean activationResult = userService.activateUserByHash(activationToken);
        if (activationResult) {
            session.setAttribute(ACTIVATION_FEEDBACK, MESSAGE_SUCCESS_ACTIVATION);
        } else {
            session.setAttribute(ACTIVATION_FEEDBACK, MESSAGE_FAIL_ACTIVATION);
        }
        return new Router(ACTIVATE_USER_PAGE, FORWARD);
    }
}