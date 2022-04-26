package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.ItemService;
import com.stakhiyevich.openadboard.service.impl.ItemServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.UploadValidator;
import com.stakhiyevich.openadboard.util.validator.impl.AddItemFormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.UploadValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PagePathHolder.ERROR_PAGE_404;
import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.EDIT_ITEM_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.*;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.ERROR;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_EDIT_ITEM_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_EDIT_ITEM_SUCCESS;

public class EditItemCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        FormValidator addItemFormValidator = AddItemFormValidator.getInstance();
        UploadValidator uploadValidator = UploadValidatorImpl.getInstance();
        ItemService itemService = ItemServiceImpl.getInstance();

        long itemId = parseLongParameter(request.getParameter(ITEM_ID));
        if (user == null || !itemService.canUserEditItem(user.getId(), itemId)) {
            return new Router(ERROR_PAGE_404, ERROR);
        }
        Router router = new Router(EDIT_ITEM_URL + "&item_id=" + itemId, REDIRECT);

        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> validationFeedback = addItemFormValidator.validateForm(requestParameters);
        if (!validationFeedback.isEmpty()) {
            session.setAttribute(EDIT_ITEM_VALIDATION_FEEDBACK, validationFeedback);
            return router;
        }

        List<Part> pictureParts;
        try {
            pictureParts = request.getParts().stream().filter(part -> PICTURE.equals(part.getName()) && part.getSize() > 0).toList();
            if (!pictureParts.isEmpty()) {
                Map<String, String> uploadValidationFeedback = uploadValidator.validateUpload(pictureParts);
                if (!uploadValidationFeedback.isEmpty()) {
                    session.setAttribute(EDIT_ITEM_VALIDATION_FEEDBACK, uploadValidator);
                    return router;
                }
            }
        } catch (IOException | ServletException e) {
            logger.error("failed to get picture parts");
            session.setAttribute(EDIT_ITEM_FEEDBACK, MESSAGE_EDIT_ITEM_FAIL);
            return router;
        }

        String title = request.getParameter(TITLE);
        BigDecimal price = BigDecimal.valueOf(parseLongParameter(request.getParameter(PRICE)));
        String description = request.getParameter(DESCRIPTION);
        String contact = request.getParameter(CONTACT);
        Long categoryId = parseLongParameter(request.getParameter(CATEGORY));
        Long cityId = parseLongParameter(request.getParameter(CITY));

        boolean result = itemService.updateItem(itemId, title, price, description, contact, categoryId, user, cityId, pictureParts);
        if (result) {
            session.setAttribute(EDIT_ITEM_FEEDBACK, MESSAGE_EDIT_ITEM_SUCCESS);
        } else {
            session.setAttribute(EDIT_ITEM_FEEDBACK, MESSAGE_EDIT_ITEM_FAIL);
        }
        return router;
    }
}
