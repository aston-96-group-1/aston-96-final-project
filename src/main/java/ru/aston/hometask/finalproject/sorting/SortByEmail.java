package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public class SortByEmail extends Sort {
    public static final String DESCRIPTION = "Сортировка по email.";

    private static final Comparator<User> BY_EMAIL = Comparator.comparing(User::getEmail);

    @Override
    public void sort(List<User> users, SortOrder sortOrder) {
        setSortOrder(sortOrder);
        quickSort(users, BY_EMAIL);
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}