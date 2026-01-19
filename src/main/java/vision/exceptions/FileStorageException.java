package vision.exceptions;

public class FileStorageException extends RuntimeException {

    public FileStorageException() {
        super("Ошибка при работе с файловой системой");
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(Throwable cause) {
        super("Ошибка при работе с файловой системой", cause);
    }

    public FileStorageException(String message) {
        super(message);
    }
}
