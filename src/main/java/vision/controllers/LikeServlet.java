package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.HiddenValidationException;
import vision.exceptions.ServiceValidationException;
import vision.services.LikeService;
import vision.services.ServiceInitializer;
import vision.validators.IdValidator;

import java.io.IOException;

@WebServlet("/like")
public class LikeServlet extends HttpServlet {

    private LikeService likeService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.likeService = services.getLikeService();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        String cardIdStr = request.getParameter("cardId");
        String action = request.getParameter("action");

        response.setContentType("application/json; charset=UTF-8");

        try {
            IdValidator.validateId(cardIdStr);
            boolean isLiked = likeService.isLikedByUser(user.getId(), cardIdStr);

            if ("like".equals(action) && !isLiked) {
                likeService.addLike(user.getId(), cardIdStr);
                isLiked = true;
            } else if ("unlike".equals(action) && isLiked) {
                likeService.deleteLike(user.getId(), cardIdStr);
                isLiked = false;
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"error\": \"Invalid action\"}");
                return;
            }

            int likesCount = likeService.getLikesCount(cardIdStr);

            response.getWriter().write("{\"success\": true, \"liked\": " + isLiked + ", \"likesCount\": " + likesCount + "}");

        } catch (DatabaseException | HiddenValidationException | ServiceValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }

    }
}
