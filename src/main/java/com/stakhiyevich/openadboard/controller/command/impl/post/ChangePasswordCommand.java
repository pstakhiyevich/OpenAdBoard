package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.ChangePasswordFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.EDIT_USER_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.NEW_PASSWORD;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.OLD_PASSWORD;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.*;

public class ChangePasswordCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(USER);
        Router router = new Router(EDIT_USER_URL + currentUser.getId(), REDIRECT);
        UserService userService = UserServiceImpl.getInstance();
        FormValidator validator = ChangePasswordFormValidator.getInstance();

        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(CHANGE_PASSWORD_VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }

        String oldPassword = request.getParameter(OLD_PASSWORD);
        String newPassword = request.getParameter(NEW_PASSWORD);
        Optional<User> user = userService.findUserByEmailAndPassword(currentUser.getEmail(), oldPassword);
        if (user.isEmpty()) {
            validationFeedback.put(OLD_PASSWORD, MESSAGE_PASSWORD_WRONG);
            session.setAttribute(CHANGE_PASSWORD_VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }
        if (oldPassword.equals(newPassword)) {
            validationFeedback.put(NEW_PASSWORD, MESSAGE_PASSWORD_WRONG);
            session.setAttribute(CHANGE_PASSWORD_VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }
        boolean result = userService.changePassword(user.get(), newPassword);
        if (result) {
            session.setAttribute(CHANGE_PASSWORD_FEEDBACK, MESSAGE_CHANGE_PASSWORD_SUCCESS);
        } else {
            session.setAttribute(CHANGE_PASSWORD_FEEDBACK, MESSAGE_CHANGE_PASSWORD_FAIL);
        }
        return router;
    }
}
