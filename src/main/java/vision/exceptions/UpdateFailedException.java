package vision.exceptions;

public class UpdateFailedException extends RuntimeException {
    public UpdateFailedException(String message) {
        super(message);
    }
    public UpdateFailedException() {
        super("Ошибка сохранения. Данные не были обновлены");
    }
}
