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

    public void launch() {
        while (true) {
            IMenuEntry menuEntry = appContext.getConsoleService().getFromMap(
                    Strings.MAIN_MENU_TITLE.get(),
                    Strings.ERROR_INVALID_MENU_ENTRY.get(),
                    menuEntryMap
            );
            menuEntry.execute();
        }
    }
}
