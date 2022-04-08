package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.SELECTED_LANGUAGE;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;

public class ChangeLanguageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String selectedLanguage = request.getParameter(SELECTED_LANGUAGE);
        switch (selectedLanguage) {
            case RUSSIAN -> {
                session.setAttribute(LOCALIZATION, RUSSIAN);
            }
            case ENGLISH -> {
                session.setAttribute(LOCALIZATION, ENGLISH);
            }
            case CHINESE -> {
                session.setAttribute(LOCALIZATION, CHINESE);
            }
        }
        return new Router(HOME_URL, REDIRECT);
    }
}
