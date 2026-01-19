package vision.validators;

import jakarta.servlet.http.Part;
import vision.exceptions.ValidationException;

public class ImageValidator {

    public static void validate(Part image) {
        if (image == null || image.getSize() == 0) {
            return;
        }

        if (image.getSize() > 10 * 1024 * 1024) {
            throw new ValidationException("Размер изображения не должен превышать 10 МБ");
        }

        String fileName = image.getSubmittedFileName().toLowerCase();

        if (!(fileName.endsWith(".png")
                || fileName.endsWith(".jpg")
                || fileName.endsWith(".jpeg"))) {
            throw new ValidationException("Разрешены изображения формата JPG или PNG");
        }
    }
}
