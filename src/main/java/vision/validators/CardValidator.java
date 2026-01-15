package vision.validators;

import vision.dto.CardInputDTO;
import vision.exceptions.ValidationException;

public class CardValidator {

    public static void validateCardCreate(CardInputDTO cardInputDTO) {
        validateName(cardInputDTO.getName());
        validateDescription(cardInputDTO.getDescription());
        CategoryValidator.validateCategoryId(cardInputDTO.getCategoryId());
    }

    public static void validateCardUpdate(CardInputDTO cardInputDTO) {
        IdValidator.validateId(cardInputDTO.getId());
        validateName(cardInputDTO.getName());
        validateDescription(cardInputDTO.getDescription());
        CategoryValidator.validateCategoryId(cardInputDTO.getCategoryId());
    }



    private static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Введите название");
        }
        if (name.length() > 100) {
            throw new ValidationException("Название не должно превышать 100 символов");
        }
    }

    private static void validateDescription(String description) {
        if (description == null || description.isBlank()) {
            throw new ValidationException("Добавьте описание");
        }
        if (description.length() > 500) {
            throw new ValidationException("Описание не должно превышать 500 символов");
        }
    }
}