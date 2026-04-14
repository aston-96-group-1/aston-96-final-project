package ru.aston.hometask.finalproject.ui;

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
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.services.ConsoleService;
import ru.aston.hometask.finalproject.services.LogService;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SortMenuEntryTest {
    @Mock
    private LogService mockLogService;

    @Mock
    private Sort mockSorter;

    @Mock
    private ConsoleService consoleService;

    @Mock
    private AppContext appContext;

    @Mock
    private SessionContext sessionContext;

    private SortMenuEntry sortMenuEntry;

    @BeforeEach
    void setup() {
        sortMenuEntry = new SortMenuEntry(appContext, sessionContext);
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertTrue(sortMenuEntry.getDescription().contains(Strings.SORT_MENU_TITLE.get()));
    }

    @Test
    void getState_WhenNotReady_ShouldReturnNotReadySign() {
        when(sessionContext.isSortUsersReady()).thenReturn(false);

        String state = sortMenuEntry.getState();

        assertEquals(StateSign.NOT_READY.getSign(), state);
    }

    @Test
    void getState_Ready_ShouldReturnReadySign() {
        when(sessionContext.isSortUsersReady()).thenReturn(true);

        String state = sortMenuEntry.getState();

        assertEquals(StateSign.READY.getSign(), state);
    }

    @Test
    void execute_WhenNotReady_ShouldShowErrorAndAndWait() {
        when(sessionContext.isSortUsersReady()).thenReturn(false);
        when(appContext.getConsoleService()).thenReturn(consoleService);

        sortMenuEntry.execute();

        verify(consoleService).printError(Strings.ERROR_USERS_SORT_NOT_READY.get());
        verify(consoleService).waitForEnter();
    }

    @Test
    void execute_WhenReady_ShouldSortAndLogUsers() {
        List<User> mockUsers = createTestUsers();
        String fakePath = "test/path.txt";

        when(sessionContext.isSortUsersReady()).thenReturn(true);
        when(sessionContext.getUsers()).thenReturn(mockUsers);
        when(sessionContext.getSorter()).thenReturn(mockSorter);
        when(sessionContext.getSortOrder()).thenReturn(SortOrder.ASC);

        when(appContext.getLogService()).thenReturn(mockLogService);
        when(mockLogService.logSorted(mockUsers)).thenReturn(fakePath);

        sortMenuEntry.execute();

        verify(mockSorter).sort(mockUsers, SortOrder.ASC);

        verify(mockLogService).logSorted(mockUsers);
    }

    private List<User> createTestUsers() {
        return Arrays.asList(
                createUser("John Doe", "password123", "john@example.com", 10),
                createUser("Jane Smith", "password456", "jane@example.com", 15)
        );
    }

    private User createUser(String name, String password, String email, int postCount) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .postCount(postCount)
                .build();
    }
}
