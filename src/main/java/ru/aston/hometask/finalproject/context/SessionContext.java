package ru.aston.hometask.finalproject.context;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.List;
import java.util.Map;

public class SessionContext {
    private IUserProvider provider;
    private List<User> users;
    private Integer size;
    private Sort sorter;
    private SortOrder sortOrder;

    public boolean isUserProviderReady() {
        return provider != null && size != null;
    }

    public boolean isSorterReady() {
        return sorter != null && sortOrder != null;
    }

    public boolean isLoadUsersReady() {
        return provider != null && size != null;
    }

    public boolean isShowUsersReady() {
        return users != null;
    }

    public boolean isSortUsersReady() {
        return users != null && sorter != null && sortOrder != null;
    }

    public void setProvider(IUserProvider provider) {
        this.provider = provider;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void setSorter(Sort sorter) {
        this.sorter = sorter;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<User> getUsers() {
        return users;
    }

    public Integer getSize() {
        return size;
    }

    public Sort getSorter() {
        return sorter;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public IUserProvider getProvider() {
        return provider;
    }
}