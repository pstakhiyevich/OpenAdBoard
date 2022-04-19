package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.BookmarkService;
import com.stakhiyevich.openadboard.service.impl.BookmarkServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.ITEM_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.ITEM_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.ADD_DELETE_BOOKMARK_FEEDBACK;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_ADD_BOOKMARK_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_ADD_BOOKMARK_SUCCESS;

public class AddBookmarkCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        BookmarkService bookmarkService = BookmarkServiceImpl.getInstance();

        long itemId = parseLongParameter(request.getParameter(ITEM_ID));

        boolean result = false;
        if (user != null && itemId != 0) {
            result = bookmarkService.addBookmark(user.getId(), itemId);
        }

        if (result) {
            session.setAttribute(ADD_DELETE_BOOKMARK_FEEDBACK, MESSAGE_ADD_BOOKMARK_SUCCESS);
        } else {
            session.setAttribute(ADD_DELETE_BOOKMARK_FEEDBACK, MESSAGE_ADD_BOOKMARK_FAIL);
        }

        return new Router(ITEM_URL + itemId, REDIRECT);
    }
}
