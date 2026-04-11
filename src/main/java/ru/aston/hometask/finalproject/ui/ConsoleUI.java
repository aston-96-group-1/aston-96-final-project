package ru.aston.hometask.finalproject.ui;

import io.github.cdimascio.dotenv.Dotenv;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.registry.Registry;
import ru.aston.hometask.finalproject.registry.SortingRegistry;
import ru.aston.hometask.finalproject.registry.UserProviderRegistry;
import ru.aston.hometask.finalproject.services.UserService;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.sorting.Sort;
import ru.aston.hometask.finalproject.common.IDescribable;

import java.util.Scanner;
import java.util.List;
import java.util.Set;

public class ConsoleUI {
    private final Registry<IUserProvider> userProviderRegistry;
    private final UserService userService;
    private final Scanner scanner;
    private final FileWriter fileWriter;
    private final Dotenv dotenv;
    private final Registry<Sort> sortingRegistry;

    private List<User> currentUsers;

    public ConsoleUI(final Registry<IUserProvider> userProviderRegistry, final UserService userService,
            final Scanner scanner, final FileWriter fileWriter, final Dotenv dotenv,
            final Registry<Sort> sortingRegistry) {
        this.userProviderRegistry = userProviderRegistry;
        this.userService = userService;
        this.scanner = scanner;
        this.fileWriter = fileWriter;
        this.dotenv = dotenv;
        this.sortingRegistry = sortingRegistry;
    }

    public void launch() {

        IUserProvider userProvider = null;
        Integer size = null;
        Sort sorter = null;
        SortOrder sortOrder = null;

        printMainMenu(userProvider, size, sorter, sortOrder);

        while (true) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    userProvider = getFromRegistry("Выберите провайдера", "Провайдера не существует",
                            userProviderRegistry);
                    size = getCollectionSize();

                    break;
                case "2":
                    sorter = getFromRegistry("Выберите сортировку", "Сортировки не существует", sortingRegistry);
                    sortOrder = getSortOrder();
                    break;
            }

            // меню + обработка
        }

    }

    private Integer getCollectionSize() {
        Integer size = null;
        System.out.println("Введите размер: ");
        do {
            try {

                size = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                size = null;
                System.out.println("Необходимо ввести число, пробуйте ещё раз: ");
            }

        } while (size == null);

        return size;
    }

    private <T extends IDescribable> T getFromRegistry(String title, String errorMessage, Registry<T> registry) {
        T result = null;

        do {
            registry.getKeys().forEach(key -> System.out.println(String.format("%s. %s", key, registry.getByKey(key))));

            String choice = scanner.nextLine().trim();

            result = registry.getByKey(choice);

            if (result == null) {

                System.out.println(String.format("%s, Попробуйте ещё раз:", errorMessage));
            }

        } while (result == null);

        return result;
    }

    // аналогично по сортировкам вместо iUser = Sort
    // ещё метод выбора порядка сортировки switch - case

    private SortOrder getSortOrder() {
        System.out.println("Выберите порядок сортировки:");
        System.out.println("1. ASC");
        System.out.println("2. DESC");
        System.out.print("Ваш выбор: ");

        SortOrder sortOrder = null;

        do {
            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    sortOrder = SortOrder.ASC;
                    break;
                case "2":
                    sortOrder = SortOrder.DESC;
                    break;
                default:
                    sortOrder = null;
                    break;
            }

        } while (sortOrder == null);

        return sortOrder;
    }

    private void printMainMenu(IUserProvider userProvider, Integer size, Sort sorter, SortOrder sortOrder) {
        System.out.println("\nГлавное меню\n");
        System.out
                .println(String.format("1. Выбрать провайдера: ", (userProvider == null || size == null ? "❌" : "✅")));
        System.out.println(String.format("2. Выбрать сортировку", (sorter == null || sortOrder == null ? "❌" : "✅")));
        System.out.println("3. Показать текущих пользователей");
        System.out.println(String.format("Выполнить сортировку",
                (userProvider == null || size == null || sorter == null || sortOrder == null ? "❌" : "✅")));
        System.out.println("0. Выход");
        System.out.println("Сделать выбор: ");
    }

    private void saveUsersToFile() {

    }

    private void printCurrentUsers() {

    }
}
