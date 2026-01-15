package vision.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeRepository {

    public boolean addLike(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO likes (user_id, card_id) VALUES (?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, cardId);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public boolean deleteLike(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM likes WHERE user_id = ? AND card_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, cardId);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public boolean isCardLikedByUser(Long userId, Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM likes WHERE user_id = ? AND card_id = ?";
        boolean liked;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, cardId);

            try (ResultSet resultSet = statement.executeQuery()) {
                liked = resultSet.next();
            }
        }

        return liked;
    }

    public int countLikesForCard(Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM likes WHERE card_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cardId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
        }
        return 0;
    }
}