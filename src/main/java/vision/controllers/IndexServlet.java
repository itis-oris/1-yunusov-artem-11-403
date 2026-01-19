package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vision.dto.UserOutputDTO;

import java.io.IOException;

@WebServlet({"/index", ""})
public class IndexServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        UserOutputDTO user = (session != null) ? (UserOutputDTO) session.getAttribute("user") : null;
        request.setAttribute("user", user);
        request.getRequestDispatcher("/index.ftlh")
                .forward(request, response);
    }
}
