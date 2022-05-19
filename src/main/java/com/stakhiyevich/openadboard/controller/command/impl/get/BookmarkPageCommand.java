package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.exception.ServiceException;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.dto.BookmarkEntityDto;
import com.stakhiyevich.openadboard.service.BookmarkService;
import com.stakhiyevich.openadboard.service.impl.BookmarkServiceImpl;
import com.stakhiyevich.openadboard.util.pagination.PageCounter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.PREVIOUS_COMMAND;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

public class BookmarkPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String currentPageCommand = request.getQueryString();
        User user = (User) session.getAttribute(USER);
        BookmarkService bookmarkService = BookmarkServiceImpl.getInstance();
        PageCounter pageCounter = PageCounter.getInstance();

        int currentPage = parseIntParameter(request.getParameter(PAGE)) != 0 ? parseIntParameter(request.getParameter(PAGE)) : DEFAULT_PAGE_NUMBER;
        int itemsPerPage = parseIntParameter(request.getParameter(ITEMS_PER_PAGE)) != 0 ? parseIntParameter(request.getParameter(ITEMS_PER_PAGE)) : DEFAULT_ITEMS_PER_PAGE;
        int numberOfItems = bookmarkService.countByUserId(user.getId());
        int numberOfPages = pageCounter.countNumberOfPages(numberOfItems, itemsPerPage);
        if (currentPage > numberOfPages) {
            return new Router(ERROR_PAGE_404, ERROR);
        }

        List<BookmarkEntityDto> bookmarks;
        try {
            bookmarks = bookmarkService.findByUserId(user.getId(), currentPage, itemsPerPage);
        } catch (ServiceException e) {
            logger.error("failed to find a bookmark by user id", e);
            return new Router(ERROR_PAGE_500, ERROR);
        }

        if (!bookmarks.isEmpty()) {
            request.setAttribute(BOOKMARKS, bookmarks);
        }

        session.setAttribute(PREVIOUS_COMMAND, currentPageCommand);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(CURRENT_PAGE, currentPage);
        request.setAttribute(ITEMS_PER_PAGE, itemsPerPage);

        return new Router(BOOKMARK_PAGE, FORWARD);
    }
}
