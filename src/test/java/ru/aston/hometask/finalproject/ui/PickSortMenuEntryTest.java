package ru.aston.hometask.finalproject.ui;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.constants.SortOrder;
import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.services.ConsoleService;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PickSortMenuEntryTest {
    @Mock
    private ConsoleService consoleService;

    @Mock
    private Scanner scanner;

    @Mock
    private AppContext appContext;

    @Mock
    private SessionContext sessionContext;

    @Mock
    private Sort sort;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private PickSortMenuEntry pickSortMenuEntry;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outContent));
        pickSortMenuEntry = new PickSortMenuEntry(appContext, sessionContext);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertTrue(pickSortMenuEntry.getDescription().contains(Strings.PICK_SORT_MENU_TITLE.get()));
    }

    @Test
    void getState_WhenNotReady_ShouldReturnNotReadySign() {
        when(sessionContext.isSorterReady()).thenReturn(false);

        String state = pickSortMenuEntry.getState();

        assertEquals(StateSign.NOT_READY.getSign(), state);
    }

    @Test
    void getState_Ready_ShouldReturnReadySign() {
        when(sessionContext.isSorterReady()).thenReturn(true);

        String state = pickSortMenuEntry.getState();

        assertEquals(StateSign.READY.getSign(), state);
    }

    @Test
    void execute_ShouldSetSortAndOrder() {
        when(appContext.getConsoleService()).thenReturn(consoleService);
        Map<String, Sort> mockMap = Map.of("1", sort);
        when(appContext.getSortMap()).thenReturn(mockMap);

        when(consoleService.getFromMap(anyString(), anyString(), eq(mockMap))).thenReturn(sort);

        when(appContext.getScanner()).thenReturn(scanner);
        when(scanner.nextLine()).thenReturn("1");

        pickSortMenuEntry.execute();

        verify(sessionContext).setSorter(sort);
        verify(sessionContext).setSortOrder(SortOrder.ASC);
    }
}
