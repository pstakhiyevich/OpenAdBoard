package com.stakhiyevich.openadboard.controller.command.impl.post;

import com.stakhiyevich.openadboard.controller.command.Command;
import com.stakhiyevich.openadboard.controller.command.Router;
import com.stakhiyevich.openadboard.model.entity.User;
import com.stakhiyevich.openadboard.model.entity.UserRole;
import com.stakhiyevich.openadboard.service.CommentService;
import com.stakhiyevich.openadboard.service.impl.CommentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static com.stakhiyevich.openadboard.controller.command.PageUrlHolder.ITEM_URL;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.COMMENT_ID;
import static com.stakhiyevich.openadboard.controller.command.RequestParameterHolder.ITEM_ID;
import static com.stakhiyevich.openadboard.controller.command.RoutingTypeHolder.REDIRECT;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.ADD_DELETE_COMMENT_FEEDBACK;
import static com.stakhiyevich.openadboard.controller.command.SessionAttributeHolder.USER;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_COMMENT_DELETE_FAIL;
import static com.stakhiyevich.openadboard.util.MessageKey.MESSAGE_COMMENT_DELETE_SUCCESS;

public class DeleteCommentCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long itemId = parseLongParameter(request.getParameter(ITEM_ID));
        CommentService commentService = CommentServiceImpl.getInstance();
        Router router = new Router(ITEM_URL + itemId, REDIRECT);
        long commentId = parseLongParameter(request.getParameter(COMMENT_ID));

        if (user.getRole().equals(UserRole.MODER) || user.getRole().equals(UserRole.ADMIN) || commentService.userCanDeleteComment(user.getId(), commentId)) {
            boolean result = commentService.deleteComment(commentId);
            if (result) {
                session.setAttribute(ADD_DELETE_COMMENT_FEEDBACK, MESSAGE_COMMENT_DELETE_SUCCESS);
            } else {
                session.setAttribute(ADD_DELETE_COMMENT_FEEDBACK, MESSAGE_COMMENT_DELETE_FAIL);
            }
        }
        return router;
    }
}
