package main.java.ru.aston.hometask.finalproject.services.sorting;

import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public class SortByName extends Sort{
    private static final Comparator<User> BY_NAME = Comparator.comparing(User::getName);
    @Override
    public void sort(List<User> users) {
        quickSort(users, BY_NAME);
    }
}
