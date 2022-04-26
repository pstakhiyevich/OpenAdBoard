package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.UploadValidator;
import com.stakhiyevich.openadboard.util.validator.impl.EditUserFormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.UploadValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.EDIT_USER_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_EDIT_USER_INFORMATION_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_EDIT_USER_INFORMATION_SUCCESS;

public class EditUserProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        UserService userService = UserServiceImpl.getInstance();
        User user = (User) session.getAttribute(USER);
        Router router = new Router(EDIT_USER_URL + user.getId(), REDIRECT);
        FormValidator validator = EditUserFormValidator.getInstance();
        UploadValidator uploadValidator = UploadValidatorImpl.getInstance();

        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = validator.validateForm(requestParameters);
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(EDIT_USER_INFORMATION_VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }
        String userName = request.getParameter(NAME);
        String userEmail = request.getParameter(EMAIL);

        List<Part> avatarParts;
        try {
            avatarParts = request.getParts().stream().filter(part -> AVATAR.equals(part.getName()) && part.getSize() > 0).toList();
            if (!avatarParts.isEmpty()) {
                Map<String, String> uploadValidationFeedback = uploadValidator.validateUpload(avatarParts);
                if (!uploadValidationFeedback.isEmpty()) {
                    session.setAttribute(EDIT_USER_INFORMATION_VALIDATION_FEEDBACK, uploadValidationFeedback);
                    return router;
                }
            }
        } catch (IOException | ServletException e) {
            logger.error("failed to get picture parts");
            session.setAttribute(EDIT_USER_INFORMATION_FEEDBACK, MESSAGE_EDIT_USER_INFORMATION_FAIL);
            return router;
        }
        boolean result = userService.updateUser(user, userName, userEmail, avatarParts);
        if (result) {
            session.setAttribute(EDIT_USER_INFORMATION_FEEDBACK, MESSAGE_EDIT_USER_INFORMATION_SUCCESS);
        } else {
            session.setAttribute(EDIT_USER_INFORMATION_FEEDBACK, MESSAGE_EDIT_USER_INFORMATION_FAIL);
        }
        return router;
    }
}