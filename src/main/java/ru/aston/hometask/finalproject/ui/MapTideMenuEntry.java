package ru.aston.hometask.finalproject.ui;

import java.util.Map;

import ru.aston.hometask.finalproject.common.IDescribable;
import ru.aston.hometask.finalproject.context.AppContext;

public abstract class MapTideMenuEntry {

    private final AppContext appContext;

    public MapTideMenuEntry(final AppContext appContext) {
        this.appContext = appContext;
    }

    protected <T extends IDescribable> T getFromMap(String title, String errorMessage, Map<String, T> map) {
        T result = null;

        do {
            map.keySet()
                    .forEach(key -> System.out.println(String.format("%s. %s", key, map.get(key).getDescription())));

            String choice = appContext.getScanner().nextLine().trim();

            result = map.get(choice);

            if (result == null) {

                System.out.println(String.format("%s, Попробуйте ещё раз:", errorMessage));
            }

        } while (result == null);

        return result;
    }

}
