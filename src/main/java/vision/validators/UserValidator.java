package vision.validators;

import vision.dto.UserInputDTO;
import vision.exceptions.ValidationException;

public class UserValidator {

    public static void validateUser(UserInputDTO userInputDTO) {
        validateUsername(userInputDTO.getUsername());
        validateEmail(userInputDTO.getEmail());
        validateFirstName(userInputDTO.getFirstName());
        validateLastName(userInputDTO.getLastName());
        validateDescription(userInputDTO.getDescription());
    }

    public static void validateLogin(String username, String password) {
        validateUsername(username);
        validatePassword(password);
    }

    public static void validatePasswordChange(String currentPassword, String newPassword, String confirmPassword) {
        validatePassword(currentPassword);
        validatePassword(newPassword);
    }

    public static void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new ValidationException("Введите пароль");
        }
        if (password.length() < 7) {
            throw new ValidationException("Пароль должен содержать не менее 7 символов");
        }
    }



    //PRIVATE

    private static void validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new ValidationException("Введите имя пользователя");
        }
        if (username.length() < 3 || username.length() > 50) {
            throw new ValidationException("Имя пользователя должно быть от 3 до 50 символов");
        }
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ValidationException("Введите email");
        }
        if (email.length() > 100) {
            throw new ValidationException("Email не должен превышать 100 символов ");
        }
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            throw new ValidationException("Некорректный формат email");
        }
    }

    private static void validateFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("Введите имя");
        }
        if (firstName.length() > 50) {
            throw new ValidationException("Имя не должно превышать 50 символов");
        }
    }

    private static void validateLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new ValidationException("Введите фамилию");
        }
        if (lastName.length() > 50) {
            throw new ValidationException("Фамилия не должна превышать 50 символов");
        }
    }

    private static void validateDescription(String description) {
        if (description != null && !description.isBlank()) {
            if (description.length() > 255) {
                throw new ValidationException("Описание не должно превышать 255 символов");
            }
        }
    }
}