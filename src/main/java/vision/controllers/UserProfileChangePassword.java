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
import vision.exceptions.UpdateFailedException;
import vision.exceptions.ValidationException;
import vision.services.ServiceInitializer;
import vision.services.UserService;
import vision.validators.UserValidator;

import java.io.IOException;

@WebServlet("/profile/change-password")
public class UserProfileChangePassword extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.userService = services.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/profile_change_password.ftlh")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        UserOutputDTO user = (UserOutputDTO) session.getAttribute("user");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            UserValidator.validatePasswordChange(currentPassword, newPassword, confirmPassword);
            userService.changePassword(user.getUsername(), currentPassword, newPassword, confirmPassword);
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (ValidationException | ServiceValidationException | UpdateFailedException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("/profile_change_password.ftlh")
                    .forward(request, response);

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}