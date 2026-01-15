package vision.repositories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import vision.config.AppConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private static DataSource dataSource;

    public static void init() throws ClassNotFoundException {
        if (dataSource != null) return;

        Class.forName("org.postgresql.Driver");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(AppConfig.DB_URL);
        config.setUsername(AppConfig.DB_USER);
        config.setPassword(AppConfig.DB_PASSWORD);
        config.setConnectionTimeout(50000);
        config.setMaximumPoolSize(10);

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (dataSource == null) {
            init();
        }
        return dataSource.getConnection();
    }

    public static void destroy() {
        if (dataSource != null) {
            ((HikariDataSource) dataSource).close();
        }
    }

}
