package com.stakhiyevich.openadboard.controller;

import com.stakhiyevich.openadboard.controller.command.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.COMMAND;

@WebServlet(name = "Controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processCommand(req, resp);
    }

    private void processCommand(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(COMMAND);
        Command command = CommandFactory.getInstance().getCommand(commandName);
        Router router = command.execute(req);
        RoutingTypeHolder routingTypeHolder = router.getRoutingType();
        String resultPage = router.getResultPage();
        switch (routingTypeHolder) {
            case FORWARD ->
                    req.getRequestDispatcher(resultPage).forward(req, resp);
            case REDIRECT ->
                    resp.sendRedirect(req.getContextPath() + router.getResultPage());
            case ERROR ->
                    resp.sendError(resultPage.equals(PagePathHolder.ERROR_PAGE_404) ? 404 : 500);
            default -> {
                logger.error("wrong routing type");
                resp.sendError(500);
            }
        }
    }
}
