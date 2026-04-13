package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class SortMenuEntry implements IMenuEntry {
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public SortMenuEntry(AppContext appContext, SessionContext sessionContext) {

        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return Strings.SORT_MENU_TITLE.get();
    }

    @Override
    public String getState() {
        return sessionContext.isSortUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        if (!sessionContext.isSortUsersReady()) {
            appContext.getConsoleService().printError(Strings.ERROR_USERS_SORT_NOT_READY.get());
            appContext.getConsoleService().waitForEnter();

            return;
        }
        sessionContext.getSorter().sort(sessionContext.getUsers(), sessionContext.getSortOrder());
    }

}
