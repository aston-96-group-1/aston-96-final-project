package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.context.AppContext;

import java.util.Map;

public class ConsoleUI {
    private final AppContext appContext;
    private final Map<String, IMenuEntry> menuEntryMap;

    public ConsoleUI(final AppContext appContext, final Map<String, IMenuEntry> menuEntryMap) {
        this.appContext = appContext;
        this.menuEntryMap = menuEntryMap;

    }

    private void printMainMenu() {
        menuEntryMap.keySet().forEach(key -> {
            IMenuEntry menuEntry = menuEntryMap.get(key);
            System.out.println(String.format("%s. %s %s", key, menuEntry.getDescription(), menuEntry.getState()));
        });
    }

    public void launch() {
        while (true) {
            IMenuEntry menuEntry;
            printMainMenu();
            do {
                String choice = appContext.getScanner().nextLine();
                menuEntry = menuEntryMap.get(choice);
                if (menuEntry == null) {
                    System.out.println("Данного пункта не существует, попробуйте еще раз:\n");
                }
            } while (menuEntry == null);

            menuEntry.execute();
        }
    }

}
