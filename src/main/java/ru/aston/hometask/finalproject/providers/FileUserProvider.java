package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.List;
import java.util.Scanner;

public class FileUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Заполнение списка пользователей из файла json.";

    private final Scanner scanner;
    private final Validator validator;

    public FileUserProvider(final Scanner scanner, final Validator validator) {
        this.scanner = scanner;
        this.validator = validator;
    }

    @Override
    public List<User> provideUsers(Integer size) {
        // TODO: Реализовать стратегию заполнения списка пользователей из файла json

        return List.of();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
