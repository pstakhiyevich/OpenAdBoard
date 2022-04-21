package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.USER_MANAGEMENT_PAGE_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_USER_CHANGE_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_USER_CHANGE_SUCCESS;

public class SaveUserChangesCommand implements Command {

    private static final long HEAD_ADMIN_ID = 1;

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long selectedUserId = parseLongParameter(request.getParameter(SELECTED_USER_ID));
        String selectedUserStatus = request.getParameter(SELECTED_USER_STATUS);
        String selectedUserRole = request.getParameter(SELECTED_USER_ROLE);

        if (!user.getRole().equals(UserRole.ADMIN) || selectedUserId == HEAD_ADMIN_ID) {
            session.setAttribute(FEEDBACK_USER_CHANGE, MESSAGE_USER_CHANGE_FAIL);
            return new Router(USER_MANAGEMENT_PAGE_URL, REDIRECT);
        }

        boolean result = userService.saveUserStatusAndRole(selectedUserId, selectedUserStatus, selectedUserRole);
        if (result) {
            session.setAttribute(FEEDBACK_USER_CHANGE, MESSAGE_USER_CHANGE_SUCCESS);
        } else {
            session.setAttribute(FEEDBACK_USER_CHANGE, MESSAGE_USER_CHANGE_FAIL);
        }
        return new Router(USER_MANAGEMENT_PAGE_URL, REDIRECT);
    }
}
