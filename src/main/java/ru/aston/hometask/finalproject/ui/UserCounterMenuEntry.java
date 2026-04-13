package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.services.UserCounter;

public class UserCounterMenuEntry implements IMenuEntry {
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public UserCounterMenuEntry(final AppContext appContext, final SessionContext sessionContext) {
        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return Strings.USER_COUNTER_MENU_TITLE.get();
    }

    @Override
    public String getState() {
        return sessionContext.isShowUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();

    }

    private User getUser() {
        appContext.getConsoleService().printUsers(sessionContext.getUsers());
        Integer index = null;

        do {
            index = appContext.getConsoleService().getValidInt(Strings.USER_INDEX_PROMPT.get());
            if (index < 0 || index >= sessionContext.getUsers().size()) {
                appContext.getConsoleService().printError(String.format(Strings.ERROR_USER_INDEX.get(), sessionContext.getUsers().size()));
                index = null;
            }

        } while (index == null);
        return sessionContext.getUsers().get(index);
    }

    @Override
    public void execute() {
        if (!sessionContext.isShowUsersReady()) {
            appContext.getConsoleService().printError(Strings.ERROR_USERS_NOT_LOADED.get());
            appContext.getConsoleService().waitForEnter();
            return;
        }
        final User target = getUser();
        int count = UserCounter.countUsers(sessionContext.getUsers(), target);
        final String path = appContext.getLogService().logFound(target, count);
        System.out.println(String.format(Strings.DATA_LOGGED_TO.get(), path));
    }
}