package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public class ManualUserProvider implements IUserProvider {

    @Override
    public List<User> provideUsers(Integer size) {
        // TODO: Реализовать стратегию заполнения списка пользователей из консоли
        return List.of();
    }
}
