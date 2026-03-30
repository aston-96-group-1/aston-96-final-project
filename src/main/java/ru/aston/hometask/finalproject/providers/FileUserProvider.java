package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public class FileUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Заполнение списка пользователей из файла json.";

    @Override
    public List<User> provideUsers(Integer size) {
        // TODO: Реализовать стратегию заполнения списка пользователей из файла json
        return List.of();
    }

    @Override
    public String getProviderDescription() {
        return DESCRIPTION;
    }
}
