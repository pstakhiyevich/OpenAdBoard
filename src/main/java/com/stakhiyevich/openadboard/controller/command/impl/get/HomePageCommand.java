package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.controller.command.PagePathHolder;
import com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.UserService;
import com.stakhiyevich.openadboard.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.USERS;

public class HomePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserService userService = new UserServiceImpl();
        List<User> users = userService.findAll();
        request.setAttribute(USERS, users);
        session.setAttribute("test", "test");
        return new Router(PagePathHolder.HOME_PAGE, RoutingTypeHolder.FORWARD);
    }
}