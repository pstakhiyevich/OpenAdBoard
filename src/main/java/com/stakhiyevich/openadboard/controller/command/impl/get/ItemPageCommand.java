package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.dto.CommentEntityDto;
import com.stakhiyevich.openadboard.service.BookmarkService;
import com.stakhiyevich.openadboard.service.CommentService;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.impl.BookmarkServiceImpl;
import com.stakhiyevich.openadboard.service.impl.CommentServiceImpl;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ITEM_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.PREVIOUS_COMMAND;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

public class ItemPageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String previousPageCommend = (String) session.getAttribute(PREVIOUS_COMMAND);
        int itemId = parseIntParameter(request.getParameter(ITEM_ID));
        ItemService itemService = ItemServiceImpl.getInstance();
        CommentService commentService = CommentServiceImpl.getInstance();
        BookmarkService bookmarkService = BookmarkServiceImpl.getInstance();

        Optional<Item> item = itemService.findItemById(itemId);
        if (item.isEmpty()) {
            return new Router(ERROR_PAGE_404, FORWARD);
        }

        boolean isBookmarked = false;
        if (user != null) {
            if (bookmarkService.isExist(user.getId(), item.get().getId())) {
                isBookmarked = true;
            }
        }

        List<CommentEntityDto> comments = commentService.findByItemId(item.get().getId());

        session.setAttribute(PREVIOUS_COMMAND, previousPageCommend);
        request.setAttribute(IS_BOOKMARKED, isBookmarked);
        request.setAttribute(COMMENTS, comments);
        request.setAttribute(ITEM, item.get());

        return new Router(ITEM_PAGE, FORWARD);
    }
}
