package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class ProviderMenuEntry implements IMenuEntry {
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public ProviderMenuEntry(final AppContext appContext, final SessionContext sessionContext) {
        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    @Override
    public String getDescription() {
        return Strings.PROVIDER_MENU_TITLE.get();
    }

    @Override
    public String getState() {
        return sessionContext.isUserProviderReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        sessionContext.setProvider(appContext.getConsoleService().getFromMap(Strings.PROVIDER_CHOOSER_TITLE.get(), Strings.ERROR_PROVIDER_NOTFOUND.get(), appContext.getProviderMap()));
        sessionContext.setSize(appContext.getConsoleService().getValidInt(Strings.USER_SIZE_INPUT_PROMPT.get()));
    }
}
