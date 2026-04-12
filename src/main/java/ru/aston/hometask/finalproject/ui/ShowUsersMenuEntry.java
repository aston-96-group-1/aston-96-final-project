package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class ShowUsersMenuEntry implements IMenuEntry {

    private static final String DESCRIPTION = "Показать текущих пользователей";
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public ShowUsersMenuEntry(AppContext appContext, SessionContext sessionContext) {

        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getState() {
        return sessionContext.isShowUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        if (!sessionContext.isShowUsersReady()) {
            System.out.println("Пользователи не загруженны\nНажмите ENTER для продолжения...");
            appContext.getScanner().nextLine();

            return;
        }
        System.out.println("Пользователи: ");
        sessionContext.getUsers().forEach(System.out::println);
    }

}
