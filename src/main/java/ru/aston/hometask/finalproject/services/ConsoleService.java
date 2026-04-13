package ru.aston.hometask.finalproject.services;

import ru.aston.hometask.finalproject.common.IDescribable;
import ru.aston.hometask.finalproject.models.User;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleService {
    private final Scanner scanner;

    public ConsoleService(final Scanner scanner) {
        this.scanner = scanner;
    }

    public <T extends IDescribable> T getFromMap(String title, String errorMessage, Map<String, T> map) {
        T result = null;

        System.out.println(title);

        do {
            map.keySet().forEach(key -> System.out.printf("%s. %s%n", key, map.get(key).getDescription()));

            String choice = scanner.nextLine().trim();

            result = map.get(choice);

            if (result == null) {
                printError(String.format("%s, Попробуйте ещё раз:", errorMessage));
            }

        } while (result == null);

        return result;
    }

    public int getValidInt(final String prompt) {
        Integer num = null;

        System.out.println(prompt);

        do {
            try {
                num = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printError("Необходимо ввести число");
            }
        } while (num == null);

        return num;
    }

    public void printUsers(final List<User> users) {
        System.out.println("Пользователи:");
        for (int i=0; i<users.size(); i++) {
            System.out.printf("%d. %s%n", i, users.get(i));
        }
    }

    public void printError(final String error) {
        System.out.println(error);
    }

    public void waitForEnter() {
        System.out.println("Нажмите ENTER для продолжения...");
        scanner.nextLine();
    }
}
