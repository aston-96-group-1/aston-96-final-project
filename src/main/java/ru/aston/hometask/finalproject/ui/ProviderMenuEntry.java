package ru.aston.hometask.finalproject.ui;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class ProviderMenuEntry extends MapTideMenuEntry implements IMenuEntry {

    private static final String DESCRIPTION = "Выбрать провайдера";
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public ProviderMenuEntry(final AppContext appContext, final SessionContext sessionContext) {

        super(appContext);

        this.appContext = appContext;
        this.sessionContext = sessionContext;

    }

    private Integer getCollectionSize() {
        Integer size = null;
        System.out.println("Введите размер: ");
        do {
            try {

                size = Integer.parseInt(appContext.getScanner().nextLine());

            } catch (NumberFormatException e) {
                size = null;
                System.out.println("Необходимо ввести число, пробуйте ещё раз: ");
            }

        } while (size == null);

        return size;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getState() {
        return sessionContext.isUserProviderReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        sessionContext.setProvider(
                getFromMap("Выберите провайдера", "Провайдера не существует", appContext.getProviderMap()));
        sessionContext.setSize(getCollectionSize());
    }

}
