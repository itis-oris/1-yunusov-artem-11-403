package vision.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vision.repositories.*;
import vision.services.ServiceInitializer;

@WebListener
public class DBContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            DBConnection.init();

            ServletContext servletContext = sce.getServletContext();

            ServiceInitializer services = new ServiceInitializer();

            servletContext.setAttribute("services", services);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBConnection.destroy();
    }
}