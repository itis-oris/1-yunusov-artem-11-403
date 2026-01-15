package vision.exceptions;

public class HiddenValidationException extends RuntimeException {
    public HiddenValidationException(String message) {
        super(message);
    }

    public HiddenValidationException() {
      super("Ошибка валидации скрытых данных");
    }

}
