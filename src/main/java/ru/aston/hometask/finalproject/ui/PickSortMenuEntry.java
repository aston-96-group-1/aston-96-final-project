package ru.aston.hometask.finalproject.ui;

import java.util.Arrays;

import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.sorting.Sort;

public class PickSortMenuEntry extends MapTideMenuEntry implements IMenuEntry {

    public final String DESCRIPTION = "Выбрать сортировку";
    private final AppContext appContext;
    private final SessionContext sessionContext;

    public PickSortMenuEntry(final AppContext appContext, final SessionContext sessionContext) {

        super(appContext);

        this.appContext = appContext;
        this.sessionContext = sessionContext;
    }

    private SortOrder getSortOrder() {
        System.out.println("Выберите порядок сортировки");
        Arrays.stream(SortOrder.values())
                .forEach(sortOrder -> System.out.println(String.format("%s. %s", sortOrder.getKey(), sortOrder)));

        SortOrder sortOrder = null;

        do {
            String choice = appContext.getScanner().nextLine().trim();

            sortOrder = SortOrder.getByKey(choice);

        } while (sortOrder == null);

        return sortOrder;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getState() {
        return sessionContext.isSorterReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();
    }

    @Override
    public void execute() {
        sessionContext
                .setSorter(getFromMap("Выберите сортировку", "Сортировки не существует", appContext.getSortMap()));
        sessionContext.setSortOrder(getSortOrder());
    }

}
