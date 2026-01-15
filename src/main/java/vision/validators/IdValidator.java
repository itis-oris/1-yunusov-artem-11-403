package vision.validators;

import vision.exceptions.HiddenValidationException;

public class IdValidator {
    public static void validateId(String id) {
        if (id == null || id.isBlank()) {
            throw new HiddenValidationException();
        }
        if (!id.matches("\\d+")) {
            throw new HiddenValidationException();
        }
    }
}
