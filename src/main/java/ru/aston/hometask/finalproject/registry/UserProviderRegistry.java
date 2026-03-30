package ru.aston.hometask.finalproject.registry;

import ru.aston.hometask.finalproject.providers.FileUserProvider;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.providers.ManualUserProvider;
import ru.aston.hometask.finalproject.providers.RandomUserProvider;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class UserProviderRegistry {
    private final Map<String, IUserProvider> providers;

    public UserProviderRegistry(final Map<String, IUserProvider> providers) {
        this.providers = providers;
    }

    public IUserProvider getProviderByKey(final String key) {
        return providers.get(key);
    }

    public List<String> getKeys() {
        return providers.keySet().stream().toList();
    }
}
