import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Predicate;

public class UserRegistry {
    private HashMap<UserIdentifier, User> users;
    private int nextId;

    public UserRegistry() {
        users = new HashMap<>();
        nextId = 1;
    }

    public void registerUser(String login, String password) {
        if (isUserRegistered(login)) {
            System.out.println("Користувач " + login + " вже зареєстрований");
            return;
        }

        UserIdentifier identifier = new UserIdentifier(nextId, login);
        User user = new User(identifier, password);
        users.put(identifier, user);
        nextId++;

        System.out.println("Користувач " + login + " успішно зареєстрований");
    }

    public void loginUser(String login, String password) {
        for (User user : users.values()) {
            if (user.getIdentifier().getName().equals(login)
                    && user.getPassword().equals(password)) {
                user.setLoggedIn(true);
                user.setLastLoginDate(LocalDateTime.now());
                System.out.println("Користувач " + login + " успішно увійшов у систему");
                return;
            }
        }

        System.out.println("Неможливо ідентифікувати або аутентифікувати користувача");
    }

    public void logoutUser(int userId) {
        for (Map.Entry<UserIdentifier, User> entry : users.entrySet()) {
            User user = entry.getValue();
            if (user.getIdentifier().getId() == userId) {
                if (user.isLoggedIn()) {
                    user.setLoggedIn(false);
                    System.out.println("Користувач " + user.getIdentifier().getName() + " вийшов із системи");
                } else {
                    System.out.println("Користувач " + user.getIdentifier().getName() + " не авторизований");
                }
                return;
            }
        }

        System.out.println("Користувача з id " + userId + " не знайдено");
    }

    public boolean isUserRegistered(String login) {
        for (User user : users.values()) {
            if (user.getIdentifier().getName().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void removeUser(int id) {
        UserIdentifier keyToRemove = null;

        for (Map.Entry<UserIdentifier, User> entry : users.entrySet()) {
            if (entry.getKey().getId() == id) {
                keyToRemove = entry.getKey();
                break;
            }
        }

        if (keyToRemove != null) {
            String removedName = keyToRemove.getName();
            users.remove(keyToRemove);
            System.out.println("Користувача " + removedName + " видалено");
        } else {
            System.out.println("Користувача з id " + id + " не знайдено");
        }
    }

    public void printTotalUsers() {
        System.out.println("Кількість користувачів: " + users.size());
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("Список користувачів порожній");
            return;
        }

        System.out.println("Усі користувачі:");
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    public LinkedList<User> getUserList() {
        return new LinkedList<>(users.values());
    }

    public LinkedList<User> getInOrder(Comparator<User> comparator) {
        LinkedList<User> list = getUserList();
        list.sort(comparator);
        return list;
    }

    public LinkedList<User> getFiltered(Predicate<User> predicate) {
        LinkedList<User> filtered = new LinkedList<>();

        for (User user : users.values()) {
            if (predicate.test(user)) {
                filtered.add(user);
            }
        }

        return filtered;
    }

    // Збереження користувачів у файл
    public void saveToFile(String fileName) {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            outputStream.writeObject(users);
            System.out.println("Користувачів успішно збережено у файл: " + fileName);

        } catch (Exception e) {
            System.out.println("Помилка під час збереження: " + e.getMessage());
        }
    }

    // Відновлення користувачів з файлу
    @SuppressWarnings("unchecked")
    public void loadFromFile(String fileName) {
        try (ObjectInputStream inputStream =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            users = (HashMap<UserIdentifier, User>) inputStream.readObject();

            // Оновлюємо nextId після завантаження
            int maxId = 0;
            for (UserIdentifier identifier : users.keySet()) {
                if (identifier.getId() > maxId) {
                    maxId = identifier.getId();
                }
            }
            nextId = maxId + 1;

            // Після відновлення всі користувачі повинні бути не залогінені
            for (User user : users.values()) {
                user.setLoggedIn(false);
            }

            System.out.println("Користувачів успішно відновлено з файлу: " + fileName);

        } catch (Exception e) {
            System.out.println("Помилка під час відновлення: " + e.getMessage());
        }
    }
}
