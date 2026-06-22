import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserRegistry {
    private Set<User> users;
    private int nextId;

    public UserRegistry() {
        users = new HashSet<>();
        nextId = 1;
    }

    public void registerUser(String login, String password) {
        User newUser = new User(nextId, login, password);

        if (users.contains(newUser)) {
            System.out.println("Користувач " + login + " вже є у списку");
            return;
        }

        users.add(newUser);
        nextId++;
        System.out.println("Користувач " + login + " успішно зареєстрований");
    }

    public void loginUser(String login, String password) {
        for (User user : users) {
            if (user.getName().equals(login) && user.getPassword().equals(password)) {
                user.setLoggedIn(true);
                user.setLastLoginDate(LocalDateTime.now());
                System.out.println("Користувач " + login + " успішно увійшов у систему");
                return;
            }
        }

        System.out.println("Неможливо ідентифікувати або аутентифікувати користувача");
    }

    public void logoutUser(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                if (user.isLoggedIn()) {
                    user.setLoggedIn(false);
                    System.out.println("Користувач " + user.getName() + " вийшов із системи");
                } else {
                    System.out.println("Користувач " + user.getName() + " не авторизований");
                }
                return;
            }
        }

        System.out.println("Користувача з id " + userId + " не знайдено");
    }

    public boolean isUserRegistered(String login) {
        for (User user : users) {
            if (user.getName().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void removeUser(int id) {
        User toRemove = null;

        for (User user : users) {
            if (user.getId() == id) {
                toRemove = user;
                break;
            }
        }

        if (toRemove != null) {
            users.remove(toRemove);
            System.out.println("Користувача " + toRemove.getName() + " видалено");
        } else {
            System.out.println("Користувача з id " + id + " не знайдено");
        }
    }

    public void printTotalUniqueUsers() {
        System.out.println("Кількість унікальних користувачів: " + users.size());
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Список користувачів порожній");
            return;
        }

        System.out.println("Усі користувачі:");
        for (User user : users) {
            System.out.println(user);
        }
    }
}
