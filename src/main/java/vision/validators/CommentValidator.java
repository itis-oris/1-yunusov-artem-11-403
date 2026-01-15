package vision.validators;

import vision.exceptions.HiddenValidationException;
import vision.exceptions.ValidationException;


public class CommentValidator {
    public static void validateContent(String content){
        if (content == null || content.isBlank()) {
            throw new HiddenValidationException();
        }
        if (content.length() > 500) {
            throw new ValidationException("Комментарий не больше 500 символов");
        }
    }
}
