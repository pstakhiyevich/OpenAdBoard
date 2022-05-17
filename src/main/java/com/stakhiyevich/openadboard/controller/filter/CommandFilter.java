package com.stakhiyevich.openadboard.controller.filter;

import com.stakhiyevich.openadboard.controller.command.CommandTypeHolder;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.EnumSet;

import static com.stakhiyevich.openadboard.controller.command.CommandTypeHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.COMMAND;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

@WebFilter(filterName = "commandFilter", urlPatterns = "/controller")
public class CommandFilter implements Filter {

    private EnumSet<CommandTypeHolder> guestCommands;
    private EnumSet<CommandTypeHolder> userCommands;
    private EnumSet<CommandTypeHolder> moderCommands;
    private EnumSet<CommandTypeHolder> adminCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        guestCommands = EnumSet.of(DEFAULT_COMMAND, HOME_PAGE, SIGN_UP_PAGE,
                ITEM_PAGE, CHANGE_LANGUAGE, SIGN_UP, ACTIVATE_USER, SIGN_IN);
        userCommands = EnumSet.of(DEFAULT_COMMAND, HOME_PAGE, SIGN_UP_PAGE,
                ADD_ITEM_PAGE, EDIT_ITEM_PAGE, ITEM_PAGE, USER_PAGE, EDIT_USER_PAGE,
                BOOKMARK_PAGE, CHANGE_LANGUAGE, SIGN_UP, ACTIVATE_USER, SIGN_IN, LOG_OUT,
                ADD_ITEM, EDIT_ITEM, DELETE_ITEM, ADD_COMMENT, DELETE_COMMENT, ADD_BOOKMARK,
                DELETE_BOOKMARK, EDIT_USER_PROFILE, CHANGE_PASSWORD);
        moderCommands = EnumSet.of(DEFAULT_COMMAND, HOME_PAGE, SIGN_UP_PAGE,
                ADD_ITEM_PAGE, EDIT_ITEM_PAGE, ITEM_PAGE, USER_PAGE, EDIT_USER_PAGE,
                CATEGORY_MANAGEMENT_PAGE, CITY_MANAGEMENT_PAGE, BOOKMARK_PAGE, CHANGE_LANGUAGE,
                SIGN_UP, ACTIVATE_USER, SIGN_IN, LOG_OUT, ADD_ITEM, EDIT_ITEM, DELETE_ITEM,
                ADD_COMMENT, DELETE_COMMENT, ADD_BOOKMARK, DELETE_BOOKMARK, EDIT_USER_PROFILE, CHANGE_PASSWORD,
                ADD_CATEGORY, DELETE_CATEGORY, EDIT_CATEGORY, ADD_CITY, EDIT_CITY, DELETE_CITY);
        adminCommands = EnumSet.of(DEFAULT_COMMAND, HOME_PAGE, SIGN_UP_PAGE,
                ADD_ITEM_PAGE, EDIT_ITEM_PAGE, ITEM_PAGE, USER_PAGE, USER_MANAGEMENT_PAGE,
                CATEGORY_MANAGEMENT_PAGE, CITY_MANAGEMENT_PAGE, EDIT_USER_PAGE,
                BOOKMARK_PAGE, CHANGE_LANGUAGE, SIGN_UP, ACTIVATE_USER, SIGN_IN, LOG_OUT,
                ADD_ITEM, EDIT_ITEM, DELETE_ITEM, ADD_COMMENT, DELETE_COMMENT, ADD_BOOKMARK,
                DELETE_BOOKMARK, EDIT_USER_PROFILE, CHANGE_PASSWORD, SAVE_USER_CHANGES,
                ADD_CATEGORY, DELETE_CATEGORY, EDIT_CATEGORY, ADD_CITY, EDIT_CITY, DELETE_CITY);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        EnumSet<CommandTypeHolder> filteredCommands;
        CommandTypeHolder requestedCommand = CommandTypeHolder.getCommandType(request.getParameter(COMMAND));
        UserRole userRole = user == null ? UserRole.GUEST : user.getRole();

        switch (userRole) {
            case GUEST -> filteredCommands = guestCommands;
            case USER -> filteredCommands = userCommands;
            case MODER -> filteredCommands = moderCommands;
            case ADMIN -> filteredCommands = adminCommands;
            default -> {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        if (!filteredCommands.contains(requestedCommand)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
