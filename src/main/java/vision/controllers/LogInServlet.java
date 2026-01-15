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
import vision.exceptions.ValidationException;
import vision.services.ServiceInitializer;
import vision.services.UserService;
import vision.validators.UserValidator;

import java.io.IOException;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.userService = services.getUserService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.ftlh")
                .forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserValidator.validateLogin(username, password);
            UserOutputDTO user = userService.login(username, password);

            HttpSession session = request.getSession(false);

            if (session != null) {
                UserOutputDTO currentUser = (UserOutputDTO) session.getAttribute("user");
                if (currentUser != null && currentUser.getUsername().equals(username)) {
                    response.sendRedirect(request.getContextPath());
                    return;
                }
                session.invalidate();
            }

            session = request.getSession(true);
            session.setAttribute("user", user);

            response.sendRedirect(request.getContextPath());

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");

        } catch (ValidationException | ServiceValidationException e) {
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("username", username);
            request.getRequestDispatcher("/login.ftlh")
                    .forward(request, response);
        }
    }
}
