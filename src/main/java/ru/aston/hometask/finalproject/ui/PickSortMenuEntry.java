package ru.aston.hometask.finalproject.ui;

import java.util.Arrays;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;

public class PickSortMenuEntry implements IMenuEntry {
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public PickSortMenuEntry(final AppContext appContext, final SessionContext sessionContext) {
        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    private SortOrder getSortOrder() {
        System.out.println(Strings.SORT_ORDER_INPUT_PROMPT.get());
        Arrays.stream(SortOrder.values()).forEach(sortOrder -> System.out.println(String.format("%s. %s", sortOrder.getKey(), sortOrder)));

        SortOrder sortOrder = null;

        do {
            String choice = appContext.getScanner().nextLine().trim();

            sortOrder = SortOrder.getByKey(choice);

        } while (sortOrder == null);

        return sortOrder;
    }

    @Override
    public String getDescription() {
        return Strings.PICK_SORT_MENU_TITLE.get();
    }

    @Override
    public String getState() {
        return sessionContext.isSorterReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        sessionContext.setSorter(appContext.getConsoleService().getFromMap(Strings.SORT_CHOOSER_TITLE.get(), Strings.ERROR_SORT_NOTFOUND.get(), appContext.getSortMap()));
        sessionContext.setSortOrder(getSortOrder());
    }
}
