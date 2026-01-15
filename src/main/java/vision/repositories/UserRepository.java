package vision.repositories;

import vision.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public User addUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO users " +
                "(username, hashed_password, email, first_name, last_name) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getFirstName());
            statement.setString(5, user.getLastName());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong("id"));
                }
            }
        }
        return user;
    }

    public boolean updateUser(User user) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET username = ?, email = ?, first_name = ?, last_name = ?, " +
                "avatar_path = ?, description = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setString(5, user.getAvatarPath());
            statement.setString(6, user.getDescription());
            statement.setLong(7, user.getId());

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public boolean updatePassword(String username, String newHashedPassword) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE users SET hashed_password = ? WHERE username = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, newHashedPassword);
            statement.setString(2, username);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public String getUserHashedPassword(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT hashed_password FROM users WHERE username = ?";
        String hashedPassword = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    hashedPassword = resultSet.getString("hashed_password");
                }
            }
        }

        return hashedPassword;
    }

    public User getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, username, hashed_password, email, first_name, last_name, avatar_path, description, role " +
                "FROM users WHERE username = ?";
        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setHashedPassword(resultSet.getString("hashed_password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setAvatarPath(resultSet.getString("avatar_path"));
                    user.setDescription(resultSet.getString("description"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        }

        return user;
    }

    public User getUserByEmail(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, username, hashed_password, email, first_name, last_name, avatar_path, description, role " +
                "FROM users WHERE email = ?";
        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setHashedPassword(resultSet.getString("hashed_password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setAvatarPath(resultSet.getString("avatar_path"));
                    user.setDescription(resultSet.getString("description"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        }

        return user;
    }

    public User getUserByUserId(Long userId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, username, hashed_password, email, first_name, last_name, avatar_path, description, role " +
                "FROM users WHERE id = ?";
        User user = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setUsername(resultSet.getString("username"));
                    user.setHashedPassword(resultSet.getString("hashed_password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setAvatarPath(resultSet.getString("avatar_path"));
                    user.setDescription(resultSet.getString("description"));
                    user.setRole(resultSet.getString("role"));
                }
            }
        }

        return user;
    }

}
