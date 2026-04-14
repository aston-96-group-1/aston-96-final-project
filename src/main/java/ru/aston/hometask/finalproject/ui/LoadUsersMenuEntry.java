package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class LoadUsersMenuEntry implements IMenuEntry {
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public LoadUsersMenuEntry(final AppContext appContext, final SessionContext sessionContext) {
        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return String.join(" ", Strings.LOAD_USERS_MENU_TITLE.get(), getState());
    }

    @Override
    public String getState() {
        return sessionContext.isLoadUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        if (!sessionContext.isUserProviderReady()) {
            appContext.getConsoleService().printError(Strings.ERROR_PROVIDER_AND_SIZE.get());
            appContext.getConsoleService().waitForEnter();
            return;
        }
        try {
            sessionContext.setUsers(sessionContext.getProvider().provideUsers(sessionContext.getSize()));
        } catch (IllegalArgumentException e) {
            sessionContext.setProvider(null);
            sessionContext.setSize(null);

            appContext.getConsoleService().printError(Strings.ERROR_SIZE_UNSUPPORTED.get());
            appContext.getConsoleService().waitForEnter();
        }
    }

}
