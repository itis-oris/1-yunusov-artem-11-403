package vision.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vision.dto.UserInputDTO;
import vision.dto.UserOutputDTO;
import vision.exceptions.DatabaseException;
import vision.exceptions.ServiceValidationException;
import vision.exceptions.UpdateFailedException;
import vision.models.User;
import vision.repositories.UserRepository;

import java.sql.SQLException;

public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutputDTO login(String username, String password) {
        User user = dbGetUserByUsername(username);

        if (user == null || !matchPassword(username, password)) {
            throw new ServiceValidationException("Неверное имя пользователя или пароль");
        }

        return new UserOutputDTO(user);
    }

    public UserOutputDTO register(UserInputDTO userInputDTO, String password) {
        validateUserCreate(userInputDTO.getUsername(), userInputDTO.getEmail());

        String hashedPassword = hashPassword(password);
        User newUser = new User(userInputDTO.getUsername(), hashedPassword, userInputDTO.getEmail(), userInputDTO.getFirstName(), userInputDTO.getLastName());
        User addedUser = dbAddUser(newUser);

        return new UserOutputDTO(addedUser);
    }

    public UserOutputDTO findUserByUserId(Long userId) {
        User user = dbGetUserByUserId(userId);
        return new UserOutputDTO(user);
    }

    public UserOutputDTO updateUser(Long userId, UserInputDTO userInputDTO) {
        validateUserUpdate(userId, userInputDTO.getUsername(), userInputDTO.getEmail());
        User user = new User(userId, userInputDTO.getUsername(), userInputDTO.getEmail(), userInputDTO.getFirstName(), userInputDTO.getLastName(), userInputDTO.getAvatarPath(), userInputDTO.getDescription());
        if(!dbUpdateUser(user)) {
            throw new UpdateFailedException("Ошибка. Данные пользователя не были обновлены");
        }
        return new UserOutputDTO(user);
    }

    public boolean changePassword(String username, String currentPassword, String newPassword, String confirmPassword) {
        validatePasswordChange(username, currentPassword, newPassword, confirmPassword);
        String newHashedPassword = hashPassword(newPassword);
        if (!dbUpdatePassword(username, newHashedPassword)) {
            throw new UpdateFailedException("Ошибка при обновлении пароля");
        }
        return true;
    }


    // PRIVATE

    private boolean matchPassword(String username, String password)  {
        String hashedPassword = dbGetUserHashedPassword(username);
        if (hashedPassword == null) return false;
        return bCrypt.matches(password, hashedPassword);
    }

    private String hashPassword(String password) {
        return bCrypt.encode(password);
    }



    //VALIDATE DATA

    private void validateUserCreate(String username, String email) {
        if (dbGetUserByUsername(username) != null) {
            throw new ServiceValidationException("Пользователь с таким именем уже существует");
        }
        if (dbGetUserByEmail(email) != null) {
            throw new ServiceValidationException("Пользователь с таким email уже существует");
        }
    }

    private void validateUserUpdate(Long userId, String username, String email) {
        User existingUser = dbGetUserByUsername(username);
        if (existingUser != null && !existingUser.getId().equals(userId)) {
            throw new ServiceValidationException("Пользователь с таким именем уже существует");
        }
        existingUser = dbGetUserByEmail(email);
        if (existingUser != null && !existingUser.getId().equals(userId)) {
            throw new ServiceValidationException("Пользователь с таким email уже существует");
        }
    }

    private void validatePasswordChange(String username, String currentPassword, String newPassword, String confirmPassword) {
        if (!matchPassword(username, currentPassword)) {
            throw new ServiceValidationException("Текущий пароль неверный");
        }
        if (matchPassword(username, newPassword)) {
            throw new ServiceValidationException("Новый пароль не должен совпадать со старым");
        }
        if (!newPassword.equals(confirmPassword)) {
            throw new ServiceValidationException("Пароли не совпадают");
        }
    }



    //DB WRAPPERS

    private User dbAddUser(User user) {
        try {
            return userRepository.addUser(user);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbUpdateUser(User user) {
        try {
            return userRepository.updateUser(user);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private boolean dbUpdatePassword(String username, String newHashedPassword) {
        try {
            return userRepository.updatePassword(username, newHashedPassword);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private String dbGetUserHashedPassword(String username) {
        try {
            return userRepository.getUserHashedPassword(username);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private User dbGetUserByUsername(String username) {
        try {
            return userRepository.getUserByUsername(username);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private User dbGetUserByEmail(String email) {
        try {
            return userRepository.getUserByEmail(email);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }

    private User dbGetUserByUserId(Long userId) {
        try {
            return userRepository.getUserByUserId(userId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException(e);
        }
    }
}