package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class ShowUsersMenuEntry implements IMenuEntry {
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public ShowUsersMenuEntry(AppContext appContext, SessionContext sessionContext) {
        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return Strings.SHOW_USERS_MENU_TITLE.get();
    }

    @Override
    public String getState() {
        return sessionContext.isShowUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        if (!sessionContext.isShowUsersReady()) {
            appContext.getConsoleService().printError(Strings.ERROR_USERS_NOT_LOADED.get());
            appContext.getConsoleService().waitForEnter();

            return;
        }
        appContext.getConsoleService().printUsers(sessionContext.getUsers());
    }

}
