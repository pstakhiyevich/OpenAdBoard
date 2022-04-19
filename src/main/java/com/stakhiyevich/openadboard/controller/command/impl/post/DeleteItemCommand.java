package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.HOME_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.ITEM_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.DELETE_ITEM_FEEDBACK;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_DELETE_ITEM_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_DELETE_ITEM_SUCCESS;

public class DeleteItemCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router = new Router(HOME_URL, REDIRECT);
        User user = (User) session.getAttribute(USER);
        int itemId = parseIntParameter(request.getParameter(ITEM_ID));
        ItemService itemService = ItemServiceImpl.getInstance();

        if (user.getRole().equals(UserRole.MODER) || user.getRole().equals(UserRole.ADMIN) || itemService.canUserEditItem(user.getId(), itemId)) {
            boolean result = itemService.deleteItemById(itemId);
            if (result) {
                session.setAttribute(DELETE_ITEM_FEEDBACK, MESSAGE_DELETE_ITEM_SUCCESS);
            } else {
                session.setAttribute(DELETE_ITEM_FEEDBACK, MESSAGE_DELETE_ITEM_FAIL);
            }
        }
        return router;
    }
}
