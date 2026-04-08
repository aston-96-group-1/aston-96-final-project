package ru.aston.hometask.finalproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.cdimascio.dotenv.Dotenv;
import ru.aston.hometask.finalproject.filesystem.FileReader;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.providers.FileUserProvider;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.providers.ManualUserProvider;
import ru.aston.hometask.finalproject.providers.RandomUserProvider;
import ru.aston.hometask.finalproject.registry.SortingRegistry;
import ru.aston.hometask.finalproject.registry.UserProviderRegistry;
import ru.aston.hometask.finalproject.services.UserService;
import ru.aston.hometask.finalproject.sorting.SortByEmail;
import ru.aston.hometask.finalproject.sorting.SortByName;
import ru.aston.hometask.finalproject.sorting.SortByPostCount;
import ru.aston.hometask.finalproject.sorting.SortByPostCountEvenOnly;
import ru.aston.hometask.finalproject.ui.ConsoleUI;
import ru.aston.hometask.finalproject.validation.Validator;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final Validator validator = new Validator();
        final Random random = new Random();
        final Gson gson = new GsonBuilder().create();
        final Dotenv dotenv = Dotenv.load();
        final FileReader fileReader = new FileReader();
        final FileWriter fileWriter = new FileWriter(gson, fileReader);

        final Map<String, IUserProvider> providerMap = new LinkedHashMap<>();
        providerMap.put("1", new ManualUserProvider(scanner, validator));
        providerMap.put("2", new RandomUserProvider(validator, random));
        providerMap.put("3", new FileUserProvider(scanner, validator, fileReader, gson));

        final Map<String, Sort> sortingMap = new LinkedHashMap<>();
        sortingMap.put("1", new SortByName());
        sortingMap.put("2", new SortByEmail());
        sortingMap.put("3", new SortByPostCount());
        sortingMap.put("4", new SortByPostCountEvenOnly());

        final UserProviderRegistry userProviderRegistry = new UserProviderRegistry(providerMap);
        final UserService userService = new UserService();

        final SortingRegistry sortingRegistry = new SortingRegistry(sortingMap);

        final ConsoleUI cli = new ConsoleUI(userProviderRegistry, userService, scanner, fileWriter, dotenv, sortingRegistry);

        cli.launch();
    }
}
