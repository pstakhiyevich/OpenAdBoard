package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.SignUpFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.SIGN_UP_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.VALIDATION_FEEDBACK;
import static com.stakhiyevich.openadboard.util.MessageKey.*;

public class SignUpCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        FormValidator validator = SignUpFormValidator.getInstance();
        Router router = new Router(SIGN_UP_URL, REDIRECT);
        UserService userService = UserServiceImpl.getInstance();

        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }

        String name = request.getParameter(NAME);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        if (userService.isEmailExist(email)) {
            validationFeedback.put(EMAIL, MESSAGE_EMAIL_EXISTS);
            session.setAttribute(VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }

        boolean result = userService.createUser(name, email, password);
        if (!result) {
            session.setAttribute(VALIDATION_FEEDBACK, MESSAGE_FAIL_REGISTRATION);
            return router;
        }
        validationFeedback.put(SUCCESS, MESSAGE_SUCCESS_REGISTRATION);
        session.setAttribute(VALIDATION_FEEDBACK, validationFeedback);
        return router;
    }
}
