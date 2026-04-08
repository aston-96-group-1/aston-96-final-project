package ru.aston.hometask.finalproject.registry;

import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.List;
import java.util.Map;

public class SortingRegistry {

    private final Map<String, Sort> sorters;

    public SortingRegistry(final Map<String, Sort> sorters) {
        this.sorters = sorters;
    }

    public Sort getProviderByKey(final String key) {
        return sorters.get(key);
    }

    public List<String> getKeys() {
        return sorters.keySet().stream().toList();
    }

}
