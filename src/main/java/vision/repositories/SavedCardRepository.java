package vision.repositories;

import vision.models.Card;
import vision.models.SavedCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SavedCardRepository {

    public boolean addSavedCard(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO saved_cards (user_id, card_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, cardId);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public boolean deleteSavedCard(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM saved_cards WHERE user_id = ? AND card_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, cardId);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public List<Card> getSavedCardsByUserId(Long userId) throws SQLException, ClassNotFoundException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT c.id, c.name, c.description, c.image_path, c.category_id, c.owner_id " +
                "FROM saved_cards sc " +
                "JOIN cards c ON sc.card_id = c.id " +
                "WHERE sc.user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Card card = new Card();
                    card.setId(resultSet.getLong("id"));
                    card.setName(resultSet.getString("name"));
                    card.setDescription(resultSet.getString("description"));
                    card.setImagePath(resultSet.getString("image_path"));
                    card.setCategoryId(resultSet.getLong("category_id"));
                    card.setUserId(resultSet.getLong("owner_id"));

                    cards.add(card);
                }
            }
        }

        return cards;
    }

    public boolean isCardSavedByUser(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM saved_cards WHERE user_id = ? AND card_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, cardId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
