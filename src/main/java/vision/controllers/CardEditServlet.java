package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vision.dto.CardInputDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.*;
import vision.models.Card;
import vision.services.*;
import vision.validators.CardValidator;
import vision.validators.IdValidator;
import vision.validators.ImageValidator;

import java.io.IOException;

@WebServlet("/card/edit")
@MultipartConfig
public class CardEditServlet extends HttpServlet {

    private CardService cardService;
    private CategoryService categoryService;
    private ImageService imageService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.cardService = services.getCardService();
        this.categoryService = services.getCategoryService();
        this.imageService = services.getImageService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        try {
            String cardIdStr = request.getParameter("id");
            IdValidator.validateId(cardIdStr);

            Card card = cardService.findCardById(cardIdStr);
            if (!cardService.cardExistsAndUserHasAccess(card, user)) {
                response.sendRedirect(request.getContextPath());
                return;
            }

            request.setAttribute("card", card);
            request.setAttribute("categories", categoryService.findAllCategories());

            request.getRequestDispatcher("/card_edit.ftlh")
                    .forward(request, response);

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        } catch (HiddenValidationException e) {
            response.sendRedirect(request.getContextPath());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        String cardIdStr = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String categoryIdStr = request.getParameter("categoryId");

        String imagePath = request.getParameter("imagePath");
        Part imagePart = request.getPart("image");
        String newImagePath = "";

        CardInputDTO cardInputDTO = new CardInputDTO(cardIdStr, name, description, imagePath, categoryIdStr);

        try {
            CardValidator.validateCardUpdate(cardInputDTO);
            ImageValidator.validate(imagePart);
        } catch (ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("card", cardInputDTO);
            try {
                request.setAttribute("categories", categoryService.findAllCategories());
            } catch (DatabaseException ee) {
                response.sendRedirect(request.getContextPath() + "/error");
            }
            request.getRequestDispatcher("/card_edit.ftlh").
                    forward(request, response);
            return;
        } catch (HiddenValidationException e) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        try {
            Card card = cardService.findCardById(cardIdStr);
            if (!cardService.cardExistsAndUserHasAccess(card, user)) {
                response.sendRedirect(request.getContextPath());
                return;
            }

            newImagePath = imageService.saveImageForUpdate(imagePart, imagePath);
            cardInputDTO.setImagePath(newImagePath);

            cardService.updateCard(user.getId(), cardInputDTO);

            if (!newImagePath.equals(imagePath)) {
                imageService.deleteImage(imagePath);
            }
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (DatabaseException e) {
            if (!newImagePath.equals(imagePath)) {
                imageService.deleteImage(newImagePath);
                cardInputDTO.setImagePath(imagePath);
            }
            response.sendRedirect(request.getContextPath() + "/error");

        } catch (ServiceValidationException | UpdateFailedException | FileStorageException e) {
            if (!newImagePath.equals(imagePath)) {
                imageService.deleteImage(newImagePath);
                cardInputDTO.setImagePath(imagePath);
            }
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("card", cardInputDTO);
            try {
                request.setAttribute("categories", categoryService.findAllCategories());
            } catch (DatabaseException ee) {
                response.sendRedirect(request.getContextPath() + "/error");
            }
            request.getRequestDispatcher("/card_edit.ftlh").
                    forward(request, response);
        }
    }
}