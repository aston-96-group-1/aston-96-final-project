package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.Strings;
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
        System.out.println(Strings.MAIN_MENU_TITLE.get());
        menuEntryMap.keySet().forEach(key -> {
            IMenuEntry menuEntry = menuEntryMap.get(key);
            System.out.println(String.format("%s. %s %s", key, menuEntry.getDescription(), menuEntry.getState()));
        });
        System.out.println();
    }

    public void launch() {
        while (true) {
            IMenuEntry menuEntry;
            printMainMenu();
            do {
                String choice = appContext.getScanner().nextLine();
                menuEntry = menuEntryMap.get(choice);
                if (menuEntry == null) {
                    System.out.println(Strings.ERROR_INVALID_MENU_ENTRY.get());
                }
            } while (menuEntry == null);

            menuEntry.execute();
        }
    }

}
