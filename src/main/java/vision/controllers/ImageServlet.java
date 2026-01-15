package vision.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vision.config.AppConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet("/uploads/*")
public class ImageServlet extends HttpServlet {

    private final String uploadDir = AppConfig.UPLOAD_DIR;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String fileName = request.getPathInfo().substring(1);

        File file = new File(uploadDir, fileName);

        if (!file.exists() || !file.isFile()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType(Files.probeContentType(file.toPath()));
        Files.copy(file.toPath(), response.getOutputStream());
    }
}
