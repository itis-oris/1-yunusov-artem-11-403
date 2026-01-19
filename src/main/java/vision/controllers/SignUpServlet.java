package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.UserInputDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.ServiceValidationException;
import vision.exceptions.ValidationException;
import vision.services.ServiceInitializer;
import vision.services.UserService;
import vision.validators.UserValidator;

import java.io.IOException;

@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.userService = services.getUserService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/signup.ftlh")
                .forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        UserInputDTO userInputDTO = new UserInputDTO(username, email, firstName, lastName);

        try {
            UserValidator.validatePassword(password);
            UserValidator.validateUser(userInputDTO);

            UserOutputDTO newUser = userService.register(userInputDTO, password);

            HttpSession session = request.getSession(false);
            if (session != null) session.invalidate();
            session =  request.getSession(true);
            session.setAttribute("user", newUser);

            response.sendRedirect(request.getContextPath());

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");

        } catch (ValidationException | ServiceValidationException e  ) {
            request.setAttribute("errormessage", e.getMessage());
            request.setAttribute("user", userInputDTO);
            request.getRequestDispatcher("/signup.ftlh")
                    .forward(request, response);
        }
    }
}
