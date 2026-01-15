package vision.models;

public class User {
    private Long id;
    private String username;
    private String hashedPassword;
    private String email;
    private String firstName;
    private String lastName;

    private String avatarPath;
    private String description;
    private String role;

    public User() {}

    public User(Long id, String username, String hashedPassword, String email, String firstName, String lastName,
                String avatarPath, String description, String role) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarPath = avatarPath;
        this.description = description;
        this.role = role;
    }

    public User(String username, String hashedPassword, String email, String firstName, String lastName) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(Long id, String username, String email, String firstName, String lastName,
                String avatarPath, String description) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarPath = avatarPath;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}