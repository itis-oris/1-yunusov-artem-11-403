package vision.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.HiddenValidationException;
import vision.exceptions.ServiceValidationException;
import vision.exceptions.ValidationException;
import vision.models.Comment;
import vision.services.CommentService;
import vision.services.ServiceInitializer;
import vision.validators.CommentValidator;
import vision.validators.IdValidator;


import java.io.IOException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    private CommentService commentService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.commentService = services.getCommentService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        response.setContentType("application/json; charset=UTF-8");

        try {
            String cardIdStr = request.getParameter("cardId");
            String action = request.getParameter("action");

            IdValidator.validateId(cardIdStr);

            if ("add".equals(action)) {
                String content = request.getParameter("content");
                CommentValidator.validateContent(content);

                Comment addedComment = commentService.addComment(content, user.getId(), cardIdStr);
                int commentsCount = commentService.getCommentsCount(cardIdStr);

                response.getWriter().write("{\"success\": true, \"comment\": {\"id\": " + addedComment.getId() +
                        ", \"content\": \"" + addedComment.getContent().replace("\"","\\\"") + "\", \"userId\": " + addedComment.getUserId() + "}, " +
                        "\"commentsCount\": " + commentsCount + "}");

            } else if ("delete".equals(action)) {
                String commentIdStr = request.getParameter("commentId");
                IdValidator.validateId(commentIdStr);

                boolean deleted = commentService.deleteComment(commentIdStr);
                int commentsCount = commentService.getCommentsCount(cardIdStr);

                response.getWriter().write("{\"success\": " + deleted + ", \"commentsCount\": " + commentsCount + "}");

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"error\": \"Invalid action\"}");
            }

        } catch (DatabaseException | ValidationException | HiddenValidationException | ServiceValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}