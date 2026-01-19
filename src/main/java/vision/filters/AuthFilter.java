package vision.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getServletPath();

        if (!isPublicPath(path) && (session == null || session.getAttribute("user") == null)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {
        return  path.equals("/login")
                || path.equals("/logout")
                || path.equals("/signup")
                || path.equals("/index")
                || path.equals("")
                || path.startsWith("/css/")
                || path.startsWith("/fonts/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/videos/");
    }
}