package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public abstract class Sort {

    public abstract void sort(List<User> users);


    protected void quickSort(List<User> users, Comparator<User> comparator) {
        if (users == null || users.size() <= 1) {
            return;
        }
        quickSort(users, 0, users.size() - 1, comparator);
    }

    private void quickSort(List<User> users, int low, int high, Comparator<User> comparator) {
        if (low < high) {
            int pivotIndex = partition(users, low, high, comparator);
            quickSort(users, low, pivotIndex - 1, comparator);
            quickSort(users, pivotIndex + 1, high, comparator);
        }
    }

    private int partition(List<User> users, int low, int high, Comparator<User> comparator) {
        User pivot = users.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(users.get(j), pivot) <= 0) {
                i++;
                swap(users, i, j);
            }
        }
        swap(users, i + 1, high);
        return i + 1;
    }

    private void swap(List<User> users, int i, int j) {
        User temp = users.get(i);
        users.set(i, users.get(j));
        users.set(j, temp);
    }

}
