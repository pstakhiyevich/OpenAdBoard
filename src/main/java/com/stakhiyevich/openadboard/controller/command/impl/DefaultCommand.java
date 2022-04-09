package com.stakhiyevich.openadboard.controller.command.impl;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(ERROR_PAGE_404, ERROR);
    }
}
