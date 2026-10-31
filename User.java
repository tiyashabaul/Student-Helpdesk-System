public class User {
    private final int userId;
    private final String name;
    private final String email;

    public User(int userId, String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "User name cannot be empty."
            );
        }

        if (email == null || !email.matches(
                "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                "Invalid email address."
            );
        }

        this.userId = userId;
        this.name = name.trim();
        this.email = email.trim();
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}