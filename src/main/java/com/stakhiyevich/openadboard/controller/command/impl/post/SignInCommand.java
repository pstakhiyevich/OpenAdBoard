package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserStatus;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.SignInFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import java.util.Optional;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.SIGN_IN_FEEDBACK;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.util.MessageKey.*;

public class SignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        FormValidator validator = SignInFormValidator.getInstance();
        Router router = new Router(HOME_URL, REDIRECT);
        UserService userService = UserServiceImpl.getInstance();

        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(SIGN_IN_FEEDBACK, validationFeedback);
            return router;
        }

        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);
        Optional<User> user = userService.findUserByEmailAndPassword(email, password);
        if (user.isEmpty()) {
            validationFeedback.put(FAIL, MESSAGE_FAIL_SING_IN);
            session.setAttribute(SIGN_IN_FEEDBACK, validationFeedback);
            return router;
        }
        if (user.get().getStatus().equals(UserStatus.INACTIVATED)) {
            validationFeedback.put(FAIL, MESSAGE_FAIL_IS_NOT_ACTIVATED);
            session.setAttribute(SIGN_IN_FEEDBACK, validationFeedback);
            return router;
        }
        if (user.get().getStatus().equals(UserStatus.BANNED)) {
            validationFeedback.put(FAIL, MESSAGE_FAIL_IS_BANNED);
            session.setAttribute(SIGN_IN_FEEDBACK, validationFeedback);
            return router;
        }
        session.setAttribute(USER, user.get());
        return router;
    }
}
