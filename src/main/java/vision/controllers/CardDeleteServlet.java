package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.HiddenValidationException;
import vision.exceptions.ServiceValidationException;
import vision.exceptions.UpdateFailedException;
import vision.models.Card;
import vision.services.*;
import vision.validators.IdValidator;

import java.io.IOException;


@WebServlet("/card/delete")
public class CardDeleteServlet extends HttpServlet {

    private CardService cardService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.cardService = services.getCardService();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        String cardIdStr = request.getParameter("id");

        try {
            IdValidator.validateId(cardIdStr);
            Card card = cardService.findCardById(cardIdStr);
            if (!cardService.cardExistsAndUserHasAccess(card, user)) {
                response.sendRedirect(request.getContextPath());
                return;
            }
            cardService.deleteCard(card);
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        } catch (HiddenValidationException | ServiceValidationException | UpdateFailedException e ) {
            response.sendRedirect(request.getContextPath());
        }
    }
}