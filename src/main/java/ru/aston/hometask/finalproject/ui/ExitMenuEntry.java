package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.Strings;

public class ExitMenuEntry implements IMenuEntry {
    @Override
    public String getDescription() {
        return Strings.EXIT_MENU_TITLE.get();
    }

    @Override
    public String getState() {
        return "";
    }

    @Override
    public void execute() {
        System.exit(0);
    }

}
