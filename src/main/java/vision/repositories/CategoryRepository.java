package vision.repositories;

import vision.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    public Category addCategory(Category category) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO categories (name, image_path) VALUES (?, ?) RETURNING id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getImagePath());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category.setId(resultSet.getLong("id"));
                }
            }
        }
        return category;
    }

    public boolean updateCategory(Category category) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE categories SET name = ?, image_path = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getName());
            statement.setString(2, category.getImagePath());
            statement.setLong(3, category.getId());

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public Category getCategoryById(Long categoryId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, name, image_path FROM categories WHERE id = ?";
        Category category = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    category = new Category();
                    category.setId(resultSet.getLong("id"));
                    category.setName(resultSet.getString("name"));
                    category.setImagePath(resultSet.getString("image_path"));
                }
            }
        }

        return category;
    }

    public List<Category> getAllCategories() throws SQLException, ClassNotFoundException {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name, image_path FROM categories";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getLong("id"));
                category.setName(resultSet.getString("name"));
                category.setImagePath(resultSet.getString("image_path"));

                categories.add(category);
            }
        }

        return categories;
    }

    public boolean deleteCategory(Long categoryId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }
}
