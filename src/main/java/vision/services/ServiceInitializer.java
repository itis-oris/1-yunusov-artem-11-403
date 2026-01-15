package vision.services;

import vision.repositories.*;

public class ServiceInitializer {

    private final UserRepository userRepository = new UserRepository();
    private final CardRepository cardRepository = new CardRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    private final CommentRepository commentRepository = new CommentRepository();
    private final LikeRepository likeRepository = new LikeRepository();
    private final SavedCardRepository savedCardRepository = new SavedCardRepository();

    public final ImageService imageService = new ImageService();
    private final UserService userService = new UserService(userRepository);
    private final CardService cardService = new CardService(cardRepository, imageService);
    private final CategoryService categoryService = new CategoryService(categoryRepository);
    private final CommentService commentService = new CommentService(commentRepository, userService);
    private final LikeService likeService = new LikeService(likeRepository);
    private final SavedCardService savedCardService = new SavedCardService(savedCardRepository);

    public UserService getUserService() { return userService; }
    public CardService getCardService() { return cardService; }
    public CategoryService getCategoryService() { return categoryService; }
    public CommentService getCommentService() { return commentService; }
    public LikeService getLikeService() { return likeService; }
    public SavedCardService getSavedCardService() { return savedCardService; }
    public ImageService getImageService() { return imageService; }
}