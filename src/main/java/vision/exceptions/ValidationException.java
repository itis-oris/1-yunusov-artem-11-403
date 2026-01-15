package vision.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
    public ValidationException() {
        super("Ошибка валидации данных");
    }
}
