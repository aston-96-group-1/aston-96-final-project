package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public interface IUserProvider {
    List<User> provideUsers(final Integer size);
}
