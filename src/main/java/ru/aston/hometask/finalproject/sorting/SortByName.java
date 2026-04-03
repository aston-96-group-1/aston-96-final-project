package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public class SortByName extends Sort {
    public static final String DESCRIPTION = "Сортировка по имени.";

    private static final Comparator<User> BY_NAME = Comparator.comparing(User::getName);

    @Override
    public void sort(List<User> users, SortOrder sortOrder) {
        setSortOrder(sortOrder);
        quickSort(users, BY_NAME);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}