package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.CommentDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.HiddenValidationException;
import vision.exceptions.ServiceValidationException;
import vision.models.Card;
import vision.services.*;
import vision.validators.IdValidator;

import java.io.IOException;
import java.util.List;

@WebServlet("/card")
public class CardViewServlet extends HttpServlet {
    private UserService userService;
    private CardService cardService;
    private SavedCardService savedCardService;
    private CategoryService categoryService;
    private CommentService commentService;
    private LikeService likeService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.userService = services.getUserService();
        this.cardService = services.getCardService();
        this.savedCardService = services.getSavedCardService();
        this.categoryService = services.getCategoryService();
        this.commentService = services.getCommentService();
        this.likeService = services.getLikeService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        request.setAttribute("user", user);

        String cardIdStr = request.getParameter("id");

        try {
            IdValidator.validateId(cardIdStr);
            Card card = cardService.findCardById(cardIdStr);
            if(card == null){
                response.sendRedirect(request.getContextPath());
                return;
            }
            request.setAttribute("card", card);

            UserOutputDTO author = userService.findUserByUserId(card.getUserId());
            request.setAttribute("author", author);

            List<CommentDTO> comments = commentService.findCommentsWithUsers(cardIdStr);
            request.setAttribute("comments", comments);

            int commentsCount = commentService.getCommentsCount(cardIdStr);
            request.setAttribute("commentsCount", commentsCount);

            boolean likedByUser = likeService.isLikedByUser(user.getId(), cardIdStr);
            request.setAttribute("likedByUser", likedByUser);

            int likesCount = likeService.getLikesCount(cardIdStr);
            request.setAttribute("likesCount", likesCount);

            boolean savedByUser = savedCardService.isSavedByUser(user.getId(), cardIdStr);
            request.setAttribute("savedByUser", savedByUser);

            request.getRequestDispatcher("/card.ftlh").
                    forward(request, response);

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        } catch (HiddenValidationException | ServiceValidationException e) {
            response.sendRedirect(request.getContextPath());
        }
    }
}
