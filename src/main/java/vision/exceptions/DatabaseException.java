package vision.exceptions;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
    public DatabaseException() {
        super("Ошибка работы с базой данных");
    }
    public DatabaseException(Throwable cause) {
        super("Ошибка работы с базой данных", cause);
    }

}
