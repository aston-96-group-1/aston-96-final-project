package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;
import java.util.Scanner;

public class ManualUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Ручной ввод пользователей.";

    private final Scanner scanner;

    public ManualUserProvider(final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public List<User> provideUsers(Integer size) {
        // TODO: Реализовать стратегию заполнения списка пользователей из консоли

        return List.of();
    }

    @Override
    public String getProviderDescription() {
        return DESCRIPTION;
    }
}
