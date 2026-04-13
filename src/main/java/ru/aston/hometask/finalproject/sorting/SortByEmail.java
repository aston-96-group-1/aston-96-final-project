package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public class SortByEmail extends Sort {
    private static final Comparator<User> BY_EMAIL = Comparator.comparing(User::getEmail);

    @Override
    public void sort(List<User> users, SortOrder sortOrder) {
        setSortOrder(sortOrder);
        quickSort(users, BY_EMAIL);
    }

    @Override
    public String getDescription() {
        return Strings.SORT_BY_EMAIL_TITLE.get();
    }
}