package com.stakhiyevich.openadboard.controller.listener;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.CURRENT_COMMAND;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.LOCALIZATION;

public class HttpSessionListenerImpl implements HttpSessionListener {

    private static final String DEFAULT_LOCALIZATION = "en";
    private static final String DEFAULT_COMMAND = "/controller?command=home_page";

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LOCALIZATION, DEFAULT_LOCALIZATION);
        session.setAttribute(CURRENT_COMMAND, DEFAULT_COMMAND);
    }
}
