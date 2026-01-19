package vision.exceptions;

public class ServiceValidationException extends RuntimeException {
    public ServiceValidationException(String message) {
        super(message);
    }

    public ServiceValidationException() {
        super("Ошибка валидации данных");
    }
}
