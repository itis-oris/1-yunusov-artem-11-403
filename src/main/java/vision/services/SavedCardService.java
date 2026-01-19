package vision.services;

import vision.exceptions.DatabaseException;
import vision.models.Card;
import vision.repositories.SavedCardRepository;

import java.sql.SQLException;
import java.util.List;

public class SavedCardService {

    private final SavedCardRepository savedCardRepository;

    public SavedCardService(SavedCardRepository savedCardRepository) {
        this.savedCardRepository = savedCardRepository;
    }

    public boolean addSavedCard(Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbAddSavedCard(userId, cardId);
    }

    public boolean deleteSavedCard(Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbDeleteSavedCard(userId, cardId);
    }

    public List<Card> getUserSavedCards(Long userId) {
        return dbGetSavedCardsByUserId(userId);
    }

    public boolean isSavedByUser(Long userId, String cardIdStr) {
        Long cardId = ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbIsCardSavedByUser(userId, cardId);
    }



    //DB WRAPPERS

    private boolean dbAddSavedCard(Long userId, Long cardId) {
        try {
            return savedCardRepository.addSavedCard(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbDeleteSavedCard(Long userId, Long cardId) {
        try {
            return savedCardRepository.deleteSavedCard(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private List<Card> dbGetSavedCardsByUserId(Long userId) {
        try {
            return savedCardRepository.getSavedCardsByUserId(userId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbIsCardSavedByUser(Long userId, Long cardId) {
        try {
            return savedCardRepository.isCardSavedByUser(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }
}
