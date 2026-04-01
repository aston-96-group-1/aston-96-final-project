package main.java.ru.aston.hometask.finalproject.services;

import main.java.ru.aston.hometask.finalproject.services.sorting.Sort;
import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public class UserService {
    // TODO: Реализовать сортировку списка пользователей
    private Sort sortStrategy;
    public void setSortStrategy(Sort sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
    public void sort(final List<User> users) {
        if (sortStrategy == null) {
            throw new IllegalStateException("Sort strategy not set");
        }
        sortStrategy.sort(users);
    }
}
