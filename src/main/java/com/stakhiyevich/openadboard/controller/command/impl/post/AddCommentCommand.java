package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.service.CommentService;
import com.stakhiyevich.openadboard.service.impl.CommentServiceImpl;
import com.stakhiyevich.openadboard.util.validator.FormValidator;
import com.stakhiyevich.openadboard.util.validator.impl.CommentFormValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.ITEM_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.COMMENT_TEXT;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.ITEM_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.*;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_COMMENT_ADD_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_COMMENT_ADD_SUCCESS;

public class AddCommentCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        long itemId = parseLongParameter(request.getParameter(ITEM_ID));
        FormValidator validator = CommentFormValidator.getInstance();
        Router router = new Router(ITEM_URL + itemId, REDIRECT);
        CommentService commentService = CommentServiceImpl.getInstance();
        User user = (User) session.getAttribute(USER);

        Map<String, String[]> requestParameters = request.getParameterMap();
        Map<String, String> addCommentFeedback = validator.validateForm(requestParameters);
        if (user == null || !addCommentFeedback.isEmpty()) {
            session.setAttribute(COMMENT_VALIDATION_FEEDBACK, addCommentFeedback);
            return router;
        }

        String text = request.getParameter(COMMENT_TEXT);
        boolean result = commentService.addComment(text, itemId, user.getId());
        if (result) {
            session.setAttribute(ADD_DELETE_COMMENT_FEEDBACK, MESSAGE_COMMENT_ADD_SUCCESS);
        } else {
            session.setAttribute(ADD_DELETE_COMMENT_FEEDBACK, MESSAGE_COMMENT_ADD_FAIL);
        }
        return router;
    }
}
