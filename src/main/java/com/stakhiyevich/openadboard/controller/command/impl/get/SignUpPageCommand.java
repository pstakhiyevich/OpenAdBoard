package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.SIGN_UP_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;

public class SignUpPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(SIGN_UP_PAGE, FORWARD);
    }
}
