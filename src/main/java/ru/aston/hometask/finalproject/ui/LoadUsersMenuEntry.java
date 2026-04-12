package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class LoadUsersMenuEntry implements IMenuEntry {

    public static final String DESCRIPTION = "Загрузить пользователей";

    private final AppContext appContext;
    private final SessionContext sessionContext;

    public LoadUsersMenuEntry(final AppContext appContext, final SessionContext sessionContext) {
        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getState() {
        return sessionContext.isLoadUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        if (!sessionContext.isUserProviderReady()) {
            System.out.println("Не выбран провайдер или размер списка!\nНажмите ENTER для продолжения...");
            appContext.getScanner().nextLine();
            return;
        }
        sessionContext.setUsers(sessionContext.getProvider().provideUsers(sessionContext.getSize()));
    }

}
