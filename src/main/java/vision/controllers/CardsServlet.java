package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.ServiceValidationException;
import vision.models.Card;
import vision.models.Category;
import vision.models.User;
import vision.services.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cards")
public class CardsServlet  extends HttpServlet {
    private CardService cardService;
    private CategoryService categoryService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.cardService = services.getCardService();
        this.categoryService = services.getCategoryService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");
        request.setAttribute("user", user);

        String query = request.getParameter("query");
        String categoryIdStr = request.getParameter("categoryId");

        List<Card> cards;

        try {
            if (query != null && !query.isBlank()) {
                cards = cardService.findCardsByKeywords(query);
                request.setAttribute("query", query);
            }
            else if (categoryIdStr != null && !categoryIdStr.isBlank()) {
                try {
                    cards = cardService.findCardsByCategoryId(categoryIdStr);
                    Category category = categoryService.findCategoryById(categoryIdStr);
                    request.setAttribute("category", category);
                } catch (ServiceValidationException e) {
                    response.sendRedirect(request.getContextPath());
                    return;
                }
            }
            else {
                cards = cardService.findAllCards();
            }

            request.setAttribute("cards", cards);

            request.getRequestDispatcher("/cards.ftlh")
                    .forward(request, response);

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}