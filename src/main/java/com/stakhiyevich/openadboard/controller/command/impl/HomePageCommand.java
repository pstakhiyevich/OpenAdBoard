package com.stakhiyevich.openadboard.controller.command.impl;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.controller.constant.PagePathHolder;
import com.stakhiyevich.openadboard.controller.constant.RoutingTypeHolder;
import jakarta.servlet.http.HttpServletRequest;

public class HomePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePathHolder.HOME_PAGE, RoutingTypeHolder.FORWARD);
    }
}
