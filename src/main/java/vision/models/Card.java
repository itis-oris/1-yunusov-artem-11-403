package vision.models;

public class Card {
    private Long id;
    private String name;
    private String description;
    private String imagePath;
    private Long categoryId;
    private Long userId;

    public Card() {}

    public Card(Long id, String name, String description, String imagePath, Long categoryId, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public Card(String name, String description, String imagePath, Long categoryId, Long userId) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
