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
import vision.validators.ImageValidator;

import java.io.IOException;

@WebServlet("/card/create")
@MultipartConfig
public class CardCreateServlet extends HttpServlet {

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
        request.setAttribute("card", new Card());
        try {
            request.setAttribute("categories", categoryService.findAllCategories());
        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        }
        request.getRequestDispatcher("/card_create.ftlh")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String categoryId = request.getParameter("categoryId");

        Part imagePart = request.getPart("image");
        String imagePath = "";

        CardInputDTO cardInputDTO = new CardInputDTO(name, description, imagePath, categoryId);

        try {
            CardValidator.validateCardCreate(cardInputDTO);
            ImageValidator.validate(imagePart);
        } catch (ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("card", cardInputDTO);
            try {
                request.setAttribute("categories", categoryService.findAllCategories());
            } catch (DatabaseException ee) {
                response.sendRedirect(request.getContextPath() + "/error");
            }
            request.getRequestDispatcher("/card_create.ftlh").
                    forward(request, response);
            return;
        } catch (HiddenValidationException e) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        try {
            imagePath = imageService.saveImage(imagePart);
            cardInputDTO.setImagePath(imagePath);

            Card createdCard = cardService.createCard(user.getId(), cardInputDTO);
            response.sendRedirect(request.getContextPath() + "/card?id=" + createdCard.getId());

        } catch (DatabaseException e) {
            imageService.deleteImage(imagePath);
            response.sendRedirect(request.getContextPath() + "/error");

        }
        catch (ServiceValidationException | FileStorageException e) {
            imageService.deleteImage(imagePath);
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("card", cardInputDTO);
            try {
                request.setAttribute("categories", categoryService.findAllCategories());
            } catch (DatabaseException ee) {
                response.sendRedirect(request.getContextPath() + "/error");
            }
            request.getRequestDispatcher("/card_create.ftlh").
                    forward(request, response);
        }
    }
}