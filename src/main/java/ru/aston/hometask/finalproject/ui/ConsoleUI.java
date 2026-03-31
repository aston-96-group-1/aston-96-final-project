package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.registry.UserProviderRegistry;
import ru.aston.hometask.finalproject.services.UserService;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.Scanner;

public class ConsoleUI {
    private final UserProviderRegistry userProviderRegistry;
    private final UserService userService;
    private final Scanner scanner;

    public ConsoleUI(final UserProviderRegistry userProviderRegistry, final UserService userService, final Scanner scanner) {
        this.userProviderRegistry = userProviderRegistry;
        this.userService = userService;
        this.scanner = scanner;
    }

    public void launch() {
        // TODO: Реализовать консольный интерфейс приложения
    }
}
