package com.stakhiyevich.openadboard.controller.command.impl.get;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.Item;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ITEM_PAGE;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.ITEM;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.ITEM_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.FORWARD;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.PREVIOUS_COMMAND;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;

public class ItemPage implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        String previousPageCommend = (String) session.getAttribute(PREVIOUS_COMMAND);
        int itemId = parseIntParameter(request.getParameter(ITEM_ID));
        ItemService itemService = ItemServiceImpl.getInstance();

        Optional<Item> item = itemService.findItemById(itemId);
        if (item.isEmpty()) {
            return new Router(ERROR_PAGE_404, FORWARD);
        }

        session.setAttribute(PREVIOUS_COMMAND, previousPageCommend);
        request.setAttribute(ITEM, item.get());

        return new Router(ITEM_PAGE, FORWARD);
    }
}
