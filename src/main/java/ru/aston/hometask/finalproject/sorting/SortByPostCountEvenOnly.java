package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortByPostCountEvenOnly extends Sort {
    public static final String DESCRIPTION = "Сортировка по количеству постов (только четные значения).";

    private static final Comparator<User> BY_POST_COUNT = Comparator.comparingInt(User::getPostCount);

    @Override
    public void sort(List<User> users, SortOrder sortOrder) {
        List<User> usersEvenPostCount = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getPostCount() % 2 == 0) {
                usersEvenPostCount.add(users.get(i));
                indexes.add(i);
            }
        }

        setSortOrder(sortOrder);
        quickSort(usersEvenPostCount, BY_POST_COUNT);

        for (int i = 0; i < indexes.size(); i++) {
            users.set(indexes.get(i), usersEvenPostCount.get(i));
        }
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}