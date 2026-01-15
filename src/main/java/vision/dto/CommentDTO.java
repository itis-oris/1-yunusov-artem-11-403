package vision.dto;

public class CommentDTO {
    private Long id;
    private String content;

    private Long userId;
    private String username;
    private String avatarPath;

    public CommentDTO() {
    }

    public CommentDTO(Long id, String content, Long userId, String username, String avatarPath) {
        this.id = id;
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.avatarPath = avatarPath;
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

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }
}
