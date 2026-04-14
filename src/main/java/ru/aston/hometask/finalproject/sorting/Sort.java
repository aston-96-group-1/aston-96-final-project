package ru.aston.hometask.finalproject.sorting;

import ru.aston.hometask.finalproject.common.IDescribable;
import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;

import java.util.Comparator;
import java.util.List;

public abstract class Sort implements IDescribable {
    private SortOrder sortOrder = SortOrder.ASC;

    public abstract void sort(List<User> users, SortOrder sortOrder);

    public abstract String getDescription();

    protected void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    protected void quickSort(List<User> users, Comparator<User> comparator) {
        if (users == null || users.size() <= 1) {
            return;
        }

        comparator = this.sortOrder == SortOrder.DESC ? comparator.reversed() : comparator;

        doQuickSort(users, 0, users.size() - 1, comparator);
    }

    private void doQuickSort(List<User> users, int low, int high, Comparator<User> comparator) {
        if (low < high) {
            int pivotIndex = partition(users, low, high, comparator);
            doQuickSort(users, low, pivotIndex - 1, comparator);
            doQuickSort(users, pivotIndex + 1, high, comparator);
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