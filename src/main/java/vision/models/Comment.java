package vision.models;

public class Comment {
    private Long id;
    private String content;
    private Long userId;
    private Long cardId;

    public Comment() {}

    public Comment(Long id, String content, Long userId, Long cardId) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.cardId = cardId;
    }

    public Comment(String content, Long userId, Long cardId) {
        this.content = content;
        this.userId = userId;
        this.cardId = cardId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
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
