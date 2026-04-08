package ru.aston.hometask.finalproject.ui;

import io.github.cdimascio.dotenv.Dotenv;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.registry.SortingRegistry;
import ru.aston.hometask.finalproject.registry.UserProviderRegistry;
import ru.aston.hometask.finalproject.services.UserService;

import java.util.Scanner;

public class ConsoleUI {
    private final UserProviderRegistry userProviderRegistry;
    private final UserService userService;
    private final Scanner scanner;
    private final FileWriter fileWriter;
    private final Dotenv dotenv;
    private final SortingRegistry sortingRegistry;

    public ConsoleUI(final UserProviderRegistry userProviderRegistry, final UserService userService,
            final Scanner scanner, final FileWriter fileWriter, final Dotenv dotenv,
            final SortingRegistry sortingRegistry) {
        this.userProviderRegistry = userProviderRegistry;
        this.userService = userService;
        this.scanner = scanner;
        this.fileWriter = fileWriter;
        this.dotenv = dotenv;
        this.sortingRegistry = sortingRegistry;
    }

    public void launch() {
        // TODO: Реализовать консольный интерфейс приложения
      
    }

}
