package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.models.User;

import java.util.List;

public class SortingStrategy {
    private Sort sortStrategy;
    public void setSortStrategy(Sort sortStrategy) {
        this.sortStrategy = sortStrategy;
    }
    public void sortStrategy(final List<User> users) {
        if (sortStrategy == null) {
            throw new IllegalStateException("Стратегия сортировки не задана");
        }
        sortStrategy.sort(users);
    }
}
