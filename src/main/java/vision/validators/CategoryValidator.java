package vision.validators;

import vision.exceptions.ValidationException;

public class CategoryValidator {
    public static void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Введите название");
        }
        if (name.length() > 100) {
            throw new ValidationException("Название не должно превышать 100 символов");
        }
    }

    public static void validateCategoryId(String categoryId) {
        IdValidator.validateId(categoryId);
        if (categoryId.equals("0")) {
            throw new ValidationException("Выберите категорию");
        }
    }
}
