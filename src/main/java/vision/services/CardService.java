package vision.services;

import vision.dto.CardInputDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.UpdateFailedException;
import vision.models.Card;
import vision.repositories.CardRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardService {

    private final CardRepository cardRepository;
    private final ImageService imageService;

    public CardService(CardRepository cardRepository, ImageService imageService) {
        this.cardRepository = cardRepository;
        this.imageService = imageService;
    }

    public Card createCard(Long userId, CardInputDTO cardInputDTO) {
        Long categoryId = ValidateAndParseIDService.ValidateAndParseLongID(cardInputDTO.getCategoryId());
        Card card = new Card(cardInputDTO.getName(), cardInputDTO.getDescription(), cardInputDTO.getImagePath(), categoryId, userId);
        return dbAddCard(card);
    }

    public Card updateCard(Long userId, CardInputDTO cardInputDTO) {
        Long cardId =  ValidateAndParseIDService.ValidateAndParseLongID(cardInputDTO.getId());
        Long categoryId = ValidateAndParseIDService.ValidateAndParseLongID(cardInputDTO.getCategoryId());
        Card card = new Card(cardId, cardInputDTO.getName(), cardInputDTO.getDescription(), cardInputDTO.getImagePath(), categoryId, userId);
        if (!dbUpdateCard(card)) {
            throw new UpdateFailedException("Ошибка. Не удалось обновить карточку");
        }
        return card;
    }

    public Card findCardById(String cardIdStr){
        Long cardId =  ValidateAndParseIDService.ValidateAndParseLongID(cardIdStr);
        return dbGetCardById(cardId);
    }

    public List<Card> findAllCards() {
        List<Card> cards = dbGetAllCards();
        return shuffleList(cards);
    }

    public List<Card> findUserCreatedCards(Long userId) {
        return dbGetCardsByUserId(userId);
    }

    public List<Card> findCardsByCategoryId(String categoryIdStr) {
        Long categoryId = ValidateAndParseIDService.ValidateAndParseLongID(categoryIdStr);
        List<Card> cards =  dbGetCardsByCategoryId(categoryId);
        return shuffleList(cards);
    }

    public List<Card> findCardsByKeywords(String query) {
        List<String> keywords = Arrays.asList(query.split("\\s+"));
        List<Card> cards = dbGetCardsByKeywords(keywords);
        return shuffleList(cards);
    }

    public boolean deleteCard(Card card) {
        if (!dbDeleteCard(card.getId())) {
            throw new UpdateFailedException("Ошибка. Не удалось удалить карточку");
        }
        imageService.deleteImage(card.getImagePath());
        return true;
    }

    public boolean cardExistsAndUserHasAccess(Card card, UserOutputDTO user){
        if (card == null || user == null) return false;
        return dbIsCardOwnedByUser(user.getId(), card.getId());
    }

    private List<Card> shuffleList(List<Card> cards) {
        List<Card> cardsShuffled = new ArrayList<>(cards);
        Collections.shuffle(cardsShuffled);
        return cardsShuffled;
    }



    //DB WRAPPERS

    private Card dbAddCard(Card card) {
        try {
            return cardRepository.addCard(card);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbUpdateCard(Card card) {
        try {
           return cardRepository.updateCard(card);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private Card dbGetCardById(Long cardId){
        try {
            return cardRepository.getCardById(cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private List<Card> dbGetAllCards() {
        try {
            return cardRepository.getAllCards();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private List<Card> dbGetCardsByCategoryId(Long categoryId) {
        try {
            return cardRepository.getCardsByCategoryId(categoryId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private List<Card> dbGetCardsByUserId(Long userId) {
        try {
            return cardRepository.getCardsByUserId(userId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private List<Card> dbGetCardsByKeywords(List<String> keywords) {
        try {
            return cardRepository.getCardsByKeywords(keywords);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbIsCardOwnedByUser(Long userId, Long cardId) {
        try {
            return cardRepository.isCardOwnedByUser(userId, cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbDeleteCard(Long cardId) {
        try {
            return cardRepository.deleteCard(cardId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }
}