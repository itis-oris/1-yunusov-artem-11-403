package vision.dto;

import vision.models.Card;

public class CardInputDTO {
    private String id;
    private String name;
    private String description;
    private String imagePath;
    private String categoryId;

    public CardInputDTO() {}

    public CardInputDTO(Card card) {
        this.id = card.getId().toString();
        this.name = card.getName();
        this.description = card.getDescription();
        this.imagePath = card.getImagePath();
        this.categoryId = card.getCategoryId().toString();
    }

    //create
    public CardInputDTO(String name, String description, String imagePath, String categoryId) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
    }

    //update
    public CardInputDTO(String id, String name, String description, String imagePath, String categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
