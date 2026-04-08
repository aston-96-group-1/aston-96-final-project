package ru.aston.hometask.finalproject.ui;

import io.github.cdimascio.dotenv.Dotenv;
import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.registry.UserProviderRegistry;
import ru.aston.hometask.finalproject.services.UserService;
import ru.aston.hometask.finalproject.sorting.Sort;
import ru.aston.hometask.finalproject.sorting.SortByPostCount;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final UserProviderRegistry userProviderRegistry;
    private final UserService userService;
    private final Scanner scanner;
    private final FileWriter fileWriter;
    private final Dotenv dotenv;

    public ConsoleUI(final UserProviderRegistry userProviderRegistry, final UserService userService, final Scanner scanner, final  FileWriter fileWriter, Dotenv dotenv) {
        this.userProviderRegistry = userProviderRegistry;
        this.userService = userService;
        this.scanner = scanner;
        this.fileWriter = fileWriter;
        this.dotenv = dotenv;
    }

    public void launch() {
        // TODO: Реализовать консольный интерфейс приложения

    }
}
