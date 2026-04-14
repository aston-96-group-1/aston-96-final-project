package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.common.IDescribable;
import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public interface IUserProvider extends IDescribable {
    List<User> provideUsers(final Integer size);

    String getDescription();
}