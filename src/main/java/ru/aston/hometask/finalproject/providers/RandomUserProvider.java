package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public class RandomUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Заполнение списка пользователей случайным образом.";

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
