package ru.aston.hometask.finalproject;

import ru.aston.hometask.finalproject.providers.FileUserProvider;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.providers.ManualUserProvider;
import ru.aston.hometask.finalproject.providers.RandomUserProvider;
import ru.aston.hometask.finalproject.registry.UserProviderRegistry;
import ru.aston.hometask.finalproject.services.UserService;
import ru.aston.hometask.finalproject.ui.ConsoleUI;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Validator validator = new Validator();

        final Map<String, IUserProvider> providerMap = new LinkedHashMap<>();
        providerMap.put("1", new ManualUserProvider(scanner, validator));
        providerMap.put("2", new RandomUserProvider(validator));
        providerMap.put("3", new FileUserProvider(scanner, validator));

        final UserProviderRegistry userProviderRegistry = new UserProviderRegistry(providerMap);
        final UserService userService = new UserService();

        final ConsoleUI cli = new ConsoleUI(userProviderRegistry, userService, scanner);

        cli.launch();
    }
}
