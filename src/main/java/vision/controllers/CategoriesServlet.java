package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vision.exceptions.DatabaseException;
import vision.models.Category;
import vision.services.CategoryService;
import vision.services.ServiceInitializer;

import java.io.IOException;
import java.util.List;

@WebServlet("/categories")
public class CategoriesServlet extends HttpServlet {

    private CategoryService categoryService;

    @Override
    public void init() {
        ServiceInitializer services = (ServiceInitializer) getServletContext().getAttribute("services");
        this.categoryService = services.getCategoryService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categories = categoryService.findAllCategories();
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/categories.ftlh")
                    .forward(request, response);

        } catch (DatabaseException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}
