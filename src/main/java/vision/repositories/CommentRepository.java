package vision.repositories;

import vision.models.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepository {

    public Comment addComment(Comment comment) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO comments (card_id, user_id, content) VALUES (?, ?, ?) RETURNING id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, comment.getCardId());
            statement.setLong(2, comment.getUserId());
            statement.setString(3, comment.getContent());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    comment.setId(resultSet.getLong("id"));
                }
            }
        }
        return comment;
    }

    public boolean deleteComment(Long commentId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM comments WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, commentId);

            int rows = statement.executeUpdate();
            return rows > 0;
        }
    }

    public List<Comment> getCommentsByCardId(Long cardId) throws SQLException, ClassNotFoundException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT id, card_id, user_id, content FROM comments WHERE card_id = ? ORDER BY id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, cardId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Comment comment = new Comment();
                    comment.setId(resultSet.getLong("id"));
                    comment.setCardId(resultSet.getLong("card_id"));
                    comment.setUserId(resultSet.getLong("user_id"));
                    comment.setContent(resultSet.getString("content"));

                    comments.add(comment);
                }
            }
        }
        return comments;
    }

    public int countCommentsForCard(Long cardId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM comments WHERE card_id = ?";

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
