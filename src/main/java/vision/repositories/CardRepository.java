package vision.repositories;

import vision.models.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    public Card addCard(Card card) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO cards (name, description, image_path, category_id, owner_id) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, card.getName());
            statement.setString(2, card.getDescription());
            statement.setString(3, card.getImagePath());
            statement.setLong(4, card.getCategoryId());
            statement.setLong(5, card.getUserId());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card.setId(resultSet.getLong("id"));
                }
            }
        }
        return card;
    }

    public boolean updateCard(Card card) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE cards SET name = ?, description = ?, image_path = ?, category_id = ? " +
                "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, card.getName());
            statement.setString(2, card.getDescription());
            statement.setString(3, card.getImagePath());
            statement.setLong(4, card.getCategoryId());
            statement.setLong(5, card.getId());

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public Card getCardById(Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT id, name, description, image_path, category_id, owner_id FROM cards WHERE id = ?";
        Card card = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cardId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = new Card();
                    card.setId(resultSet.getLong("id"));
                    card.setName(resultSet.getString("name"));
                    card.setDescription(resultSet.getString("description"));
                    card.setImagePath(resultSet.getString("image_path"));
                    card.setCategoryId(resultSet.getLong("category_id"));
                    card.setUserId(resultSet.getLong("owner_id"));
                }
            }
        }

        return card;
    }

    public List<Card> getAllCards() throws SQLException, ClassNotFoundException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT id, name, description, image_path, category_id, owner_id FROM cards";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

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

        return cards;
    }

    public List<Card> getCardsByCategoryId(Long categoryId) throws SQLException, ClassNotFoundException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT id, name, description, image_path, category_id, owner_id FROM cards WHERE category_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, categoryId);

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

    public List<Card> getCardsByUserId(Long userId) throws SQLException, ClassNotFoundException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT id, name, description, image_path, category_id, owner_id FROM cards WHERE owner_id = ? ORDER BY id";

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

    public List<Card> getCardsByKeywords(List<String> keywords) throws SQLException, ClassNotFoundException {
        if (keywords == null || keywords.isEmpty()) {
            return new ArrayList<>();
        }

        StringBuilder sql = new StringBuilder(
                "SELECT cr.id, cr.name, cr.description, cr.image_path, cr.category_id, cr.owner_id " +
                        "FROM cards cr " +
                        "JOIN categories ct ON cr.category_id = ct.id " +
                        "WHERE "
        );

        for (int i = 0; i < keywords.size(); i++) {
            if (i > 0) {
                sql.append(" OR ");
            }
            sql.append("(cr.name ILIKE ? OR cr.description ILIKE ? OR ct.name ILIKE ?)");
        }

        List<Card> cards = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            for (String keyword : keywords) {
                String pattern = "%" + keyword + "%";
                statement.setString(paramIndex++, pattern);
                statement.setString(paramIndex++, pattern);
                statement.setString(paramIndex++, pattern);
            }

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

    public boolean isCardOwnedByUser(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM cards WHERE id = ? AND owner_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cardId);
            statement.setLong(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    public boolean deleteCard(Long id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM cards WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }
}
