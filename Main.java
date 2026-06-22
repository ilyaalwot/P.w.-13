import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserRegistry registry = new UserRegistry();

        System.out.print("Чи потрібно відновити базу користувачів з файлу? (так/ні): ");
        String restoreChoice = scanner.nextLine();

        if (restoreChoice.equalsIgnoreCase("так")) {
            System.out.print("Введіть шлях до файлу: ");
            String fileName = scanner.nextLine();
            registry.loadFromFile(fileName);
        }

        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Зареєструвати користувача");
            System.out.println("2. Увійти в систему");
            System.out.println("3. Вийти з системи");
            System.out.println("4. Перевірити, чи зареєстрований користувач");
            System.out.println("5. Видалити користувача");
            System.out.println("6. Показати кількість користувачів");
            System.out.println("7. Показати всіх користувачів");
            System.out.println("8. Показати список користувачів");
            System.out.println("9. Показати користувачів у відсортованому порядку");
            System.out.println("10. Показати тільки авторизованих користувачів");
            System.out.println("11. Зберегти користувачів у файл");
            System.out.println("12. Відновити користувачів з файлу");
            System.out.println("0. Вийти");
            System.out.print("Ваш вибір: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Введіть логін: ");
                    String regLogin = scanner.nextLine();
                    System.out.print("Введіть пароль: ");
                    String regPassword = scanner.nextLine();
                    registry.registerUser(regLogin, regPassword);
                    break;

                case 2:
                    System.out.print("Введіть логін: ");
                    String login = scanner.nextLine();
                    System.out.print("Введіть пароль: ");
                    String password = scanner.nextLine();
                    registry.loginUser(login, password);
                    break;

                case 3:
                    System.out.print("Введіть id користувача: ");
                    int logoutId = Integer.parseInt(scanner.nextLine());
                    registry.logoutUser(logoutId);
                    break;

                case 4:
                    System.out.print("Введіть логін для перевірки: ");
                    String checkLogin = scanner.nextLine();
                    if (registry.isUserRegistered(checkLogin)) {
                        System.out.println("Користувач " + checkLogin + " зареєстрований");
                    } else {
                        System.out.println("Користувач " + checkLogin + " не зареєстрований");
                    }
                    break;

                case 5:
                    System.out.print("Введіть id користувача для видалення: ");
                    int removeId = Integer.parseInt(scanner.nextLine());
                    registry.removeUser(removeId);
                    break;

                case 6:
                    registry.printTotalUsers();
                    break;

                case 7:
                    registry.displayAllUsers();
                    break;

                case 8:
                    LinkedList<User> allUsers = registry.getUserList();
                    System.out.println("Список користувачів:");
                    for (User user : allUsers) {
                        System.out.println(user);
                    }
                    break;

                case 9:
                    LinkedList<User> sortedUsers = registry.getInOrder(
                            (u1, u2) -> u1.getIdentifier().getName()
                                    .compareToIgnoreCase(u2.getIdentifier().getName())
                    );

                    System.out.println("Користувачі, відсортовані за іменем:");
                    for (User user : sortedUsers) {
                        System.out.println(user);
                    }
                    break;

                case 10:
                    LinkedList<User> loggedUsers = registry.getFiltered(User::isLoggedIn);

                    System.out.println("Авторизовані користувачі:");
                    if (loggedUsers.isEmpty()) {
                        System.out.println("Немає авторизованих користувачів");
                    } else {
                        for (User user : loggedUsers) {
                            System.out.println(user);
                        }
                    }
                    break;

                case 11:
                    System.out.print("Введіть ім'я файлу для збереження: ");
                    String saveFile = scanner.nextLine();
                    registry.saveToFile(saveFile);
                    break;

                case 12:
                    System.out.print("Введіть шлях до файлу для відновлення: ");
                    String loadFile = scanner.nextLine();
                    registry.loadFromFile(loadFile);
                    break;

                case 0:
                    System.out.print("Бажаєте зберегти користувачів перед виходом? (так/ні): ");
                    String saveBeforeExit = scanner.nextLine();

                    if (saveBeforeExit.equalsIgnoreCase("так")) {
                        System.out.print("Введіть ім'я файлу для збереження: ");
                        String fileName = scanner.nextLine();
                        registry.saveToFile(fileName);
                    }

                    System.out.println("Завершення програми...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Невірний пункт меню");
            }
        }
    }
}
