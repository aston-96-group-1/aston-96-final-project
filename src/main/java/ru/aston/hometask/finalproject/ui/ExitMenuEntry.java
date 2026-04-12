package ru.aston.hometask.finalproject.ui;

public class ExitMenuEntry implements IMenuEntry {

    public static final String DESCRIPTION = "Выход";

    @Override
    public String getDescription() {
        return DESCRIPTION;
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
