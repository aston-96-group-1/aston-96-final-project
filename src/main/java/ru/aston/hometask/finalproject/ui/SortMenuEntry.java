package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class SortMenuEntry implements IMenuEntry {

    public static final String DESCRIPTION = "Отсортировать пользователей";
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public SortMenuEntry(AppContext appContext, SessionContext sessionContext) {

        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getState() {
        return sessionContext.isSortUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        if (!sessionContext.isSortUsersReady()) {

            System.out.println(
                    "Необходимо выбрать пользователей, сортировку и порядок сортировки!\nНажмите ENTER для продолжения...");
            appContext.getScanner().nextLine();

            return;
        }
        sessionContext.getSorter().sort(sessionContext.getUsers(), sessionContext.getSortOrder());
    }

}
