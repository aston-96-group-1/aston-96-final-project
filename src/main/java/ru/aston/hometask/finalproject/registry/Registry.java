package ru.aston.hometask.finalproject.registry;

import ru.aston.hometask.finalproject.common.IDescribable;
import java.util.Map;
import java.util.Set;

public class Registry<T extends IDescribable> {

    private final Map<String, T> iteams;

    public Registry(Map<String, T> iteams) {
        this.iteams = iteams;
    }

    public T getByKey(String key) {
        return iteams.get(key);
    }

    public Set<String> getKeys() {
        return iteams.keySet();
    }

}
