package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.common.IDescribable;

public interface IMenuEntry extends IDescribable {
    String getDescription();

    String getState();

    void execute();
}
