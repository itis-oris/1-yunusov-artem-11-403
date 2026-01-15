package vision.services;

import vision.exceptions.DatabaseException;
import vision.repositories.LikeRepository;

import java.sql.SQLException;

public class LikeService {

    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public boolean addLike(Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbAddLike(userId, cardId);
    }

    public boolean deleteLike(Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbDeleteLike(userId, cardId);
    }

    public Integer getLikesCount(String cardIdStr){
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbCountLikesForCard(cardId);
    }

    public boolean isLikedByUser(Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbIsCardLikedByUser(userId, cardId);
    }



    //DB WRAPPERS

    private boolean dbAddLike(Long userId, Long cardId) {
        try {
            return likeRepository.addLike(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbDeleteLike(Long userId, Long cardId) {
        try {
            return likeRepository.deleteLike(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private int dbCountLikesForCard(Long cardId) {
        try {
            return likeRepository.countLikesForCard(cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbIsCardLikedByUser(Long userId, Long cardId) {
        try {
            return likeRepository.isCardLikedByUser(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }
}