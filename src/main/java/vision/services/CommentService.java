package vision.services;

import vision.dto.CommentDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.models.Comment;
import vision.repositories.CommentRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public Comment addComment(String content, Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        Comment comment = new Comment(content, userId, cardId);
        return dbAddComment(comment);
    }

    public boolean deleteComment(String commentIdStr) {
        Long commentId = ValidateAndParseIDService.ValidateAndParseLongID(commentIdStr);
        return dbDeleteComment(commentId);
    }

    public int getCommentsCount(String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbCountCommentsForCard(cardId);
    }

    public List<CommentDTO> findCommentsWithUsers(String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        List<Comment> comments = dbGetCommentsByCardId(cardId);

        Set<Long> userIds = comments.stream()
                .map(Comment::getUserId)
                .collect(Collectors.toSet());

        Map<Long, UserOutputDTO> usersMap = new HashMap<>();
        for (Long id : userIds) {
            UserOutputDTO u = userService.findUserByUserId(id);
            if (u != null) usersMap.put(id, u);
        }

        List<CommentDTO> commentsDTO = new ArrayList<>();
        for (Comment comment : comments) {
            UserOutputDTO user = usersMap.get(comment.getUserId());

            commentsDTO.add(new CommentDTO(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUserId(),
                    user != null ? user.getUsername() : "Unknown",
                    user != null ? user.getAvatarPath() : null
            ));
        }
        return commentsDTO;
    }



    //DB WRAPPERS

    private Comment dbAddComment(Comment comment) {
        try {
            return commentRepository.addComment(comment);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e); }
    }

    private boolean dbDeleteComment(Long commentId) {
        try {
            return commentRepository.deleteComment(commentId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e); }
    }

    private List<Comment> dbGetCommentsByCardId(Long cardId) {
        try {
            return commentRepository.getCommentsByCardId(cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private int dbCountCommentsForCard(Long cardId) {
        try {
            return commentRepository.countCommentsForCard(cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }
}
