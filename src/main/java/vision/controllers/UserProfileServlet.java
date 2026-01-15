package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.models.Card;
import vision.services.CardService;
import vision.services.SavedCardService;
import vision.services.ServiceInitializer;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class UserProfileServlet extends HttpServlet {

    private CardService cardService;
    private SavedCardService savedCardService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.cardService = services.getCardService();
        this.savedCardService = services.getSavedCardService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");
        Long userId = user.getId();

        request.setAttribute("user", user);

        try {
            List<Card> createdCards = cardService.findUserCreatedCards(userId);
            request.setAttribute("createdCards", createdCards);

            List<Card> savedCards =  savedCardService.getUserSavedCards(userId);
            request.setAttribute("savedCards", savedCards);

            request.getRequestDispatcher("/profile.ftlh")
                    .forward(request, response);

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");

        }
    }
}
