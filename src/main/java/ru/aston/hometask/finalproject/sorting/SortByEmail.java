package main.java.ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public class SortByEmail extends Sort{
    private static final Comparator<User> BY_EMAIL = Comparator.comparing(User::getEmail);
    @Override
    public void sort(List<User> users) {
        quickSort(users, BY_EMAIL);
    }
}
