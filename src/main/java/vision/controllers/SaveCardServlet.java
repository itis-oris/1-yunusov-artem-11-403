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
import vision.models.User;
import vision.services.SavedCardService;
import vision.services.ServiceInitializer;
import vision.validators.IdValidator;

import java.io.IOException;

@WebServlet("/save")
public class SaveCardServlet extends HttpServlet {

    private SavedCardService savedCardService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.savedCardService = services.getSavedCardService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        String cardIdStr = request.getParameter("cardId");
        String action = request.getParameter("action");

        response.setContentType("application/json; charset=UTF-8");

        try {
            IdValidator.validateId(cardIdStr);
            boolean isSaved = savedCardService.isSavedByUser(user.getId(), cardIdStr);

            if ("save".equals(action) && !isSaved) {
                savedCardService.addSavedCard(user.getId(), cardIdStr);
                isSaved = true;
            } else if ("unsave".equals(action) && isSaved) {
                savedCardService.deleteSavedCard(user.getId(), cardIdStr);
                isSaved = false;
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"success\": false, \"error\": \"Invalid action\"}");
                return;
            }

            response.getWriter().write("{\"success\": true, \"saved\": " + isSaved + "}");

        } catch (DatabaseException | HiddenValidationException | ServiceValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"success\": false, \"error\": \"" + e.getMessage() + "\"}");
        }
    }
}