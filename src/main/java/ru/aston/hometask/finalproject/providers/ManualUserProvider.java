package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public class ManualUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Ручной ввод пользователей.";

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
