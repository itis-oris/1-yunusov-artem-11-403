package vision.models;

public class SavedCard {

    private Long userId;
    private Long cardId;

    public SavedCard() {}

    public SavedCard(Long userId, Long cardId) {
        this.userId = userId;
        this.cardId = cardId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCardId() {
        return cardId;
    }
    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
