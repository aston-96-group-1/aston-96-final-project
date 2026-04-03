package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public class SortByPostCount extends Sort{
    private static final Comparator<User> BY_POST_COUNT = Comparator.comparingInt(User::getPostCount);
    @Override
    public void sort(List<User> users) {
        quickSort(users,BY_POST_COUNT);
    }
}
