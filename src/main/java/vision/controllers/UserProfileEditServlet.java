package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vision.dto.UserInputDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.*;
import vision.services.ImageService;
import vision.services.ServiceInitializer;
import vision.services.UserService;
import vision.validators.ImageValidator;
import vision.validators.UserValidator;

import java.io.IOException;

@WebServlet("/profile/edit")
@MultipartConfig
public class UserProfileEditServlet extends HttpServlet {

    private UserService userService;
    private ImageService imageService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.userService = services.getUserService();
        this.imageService = services.getImageService();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        request.setAttribute("user", user);

        request.getRequestDispatcher("/profile_edit.ftlh")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");
        Long userId = user.getId();

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String description = request.getParameter("description");

        String avatarPath = request.getParameter("avatarPath");
        Part avatarPart = request.getPart("avatar");

        UserInputDTO userInputDTO = new UserInputDTO(username, email, firstName, lastName, avatarPath, description);
        try {
            UserValidator.validateUser(userInputDTO);
            ImageValidator.validate(avatarPart);
        } catch (ValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("user", userInputDTO);
            request.getRequestDispatcher("/profile_edit.ftlh")
                    .forward(request, response);
            return;
        }

        String newAvatarPath="";
        try {
            newAvatarPath = imageService.saveImageForUpdate(avatarPart, avatarPath);
            userInputDTO.setAvatarPath(newAvatarPath);

            UserOutputDTO updatedUser = userService.updateUser(userId, userInputDTO);
            if (updatedUser != null) {
                session.setAttribute("user", updatedUser);
                if (!newAvatarPath.equals(avatarPath)) {
                    imageService.deleteImage(avatarPath);
                }
            }
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (DatabaseException e) {
            if (!newAvatarPath.equals(avatarPath)) {
                imageService.deleteImage(newAvatarPath);
            }
            response.sendRedirect(request.getContextPath() + "/error");

        } catch (ServiceValidationException | UpdateFailedException | FileStorageException e) {
            if (!newAvatarPath.equals(avatarPath)) {
                imageService.deleteImage(newAvatarPath);
            }
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("user", userInputDTO);
            request.getRequestDispatcher("/profile_edit.ftlh")
                    .forward(request, response);
        }
    }
}