import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserIdentifier identifier;
    private String password;
    private LocalDateTime lastLoginDate;

    // Не зберігаємо стан логування в системі
    private transient boolean isLoggedIn;

    public User(UserIdentifier identifier, String password) {
        this.identifier = identifier;
        this.password = password;
        this.lastLoginDate = null;
        this.isLoggedIn = false;
    }

    public UserIdentifier getIdentifier() {
        return identifier;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    @Override
    public String toString() {
        return "User{" +
                "identifier=" + identifier +
                ", lastLoginDate=" + lastLoginDate +
                ", isLoggedIn=" + isLoggedIn +
                '}';
    }
}
