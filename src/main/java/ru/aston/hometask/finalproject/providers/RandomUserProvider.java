package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.List;

public class RandomUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Заполнение списка пользователей случайным образом.";

    private final Validator validator;

    public RandomUserProvider(final Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<User> provideUsers(Integer size) {
        // TODO: Реализовать стратегию заполнения списка пользователей случайным методом

        return List.of();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
