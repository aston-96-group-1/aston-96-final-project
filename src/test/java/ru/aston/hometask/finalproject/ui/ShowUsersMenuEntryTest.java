package ru.aston.hometask.finalproject.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.services.ConsoleService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShowUsersMenuEntryTest {
    @Mock
    private ConsoleService consoleService;

    @Mock
    private AppContext appContext;

    @Mock
    private SessionContext sessionContext;

    private ShowUsersMenuEntry showUsersMenuEntry;

    @BeforeEach
    void setup() {
        showUsersMenuEntry = new ShowUsersMenuEntry(appContext, sessionContext);
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertTrue(showUsersMenuEntry.getDescription().contains(Strings.SHOW_USERS_MENU_TITLE.get()));
    }

    @Test
    void getState_WhenNotReady_ShouldReturnNotReadySign() {
        when(sessionContext.isShowUsersReady()).thenReturn(false);

        String state = showUsersMenuEntry.getState();

        assertEquals(StateSign.NOT_READY.getSign(), state);
    }

    @Test
    void getState_Ready_ShouldReturnReadySign() {
        when(sessionContext.isShowUsersReady()).thenReturn(true);

        String state = showUsersMenuEntry.getState();

        assertEquals(StateSign.READY.getSign(), state);
    }

    @Test
    void execute_WhenNotReady_ShouldShowErrorAndAndWait() {
        when(sessionContext.isShowUsersReady()).thenReturn(false);
        when(appContext.getConsoleService()).thenReturn(consoleService);

        showUsersMenuEntry.execute();

        verify(consoleService).printError(Strings.ERROR_USERS_NOT_LOADED.get());
        verify(consoleService).waitForEnter();
    }

    @Test
    void execute_WhenReady_ShouldPrintUsers() {
        List<User> mockUsers = createTestUsers();
        when(sessionContext.isShowUsersReady()).thenReturn(true);
        when(sessionContext.getUsers()).thenReturn(mockUsers);
        when(appContext.getConsoleService()).thenReturn(consoleService);

        showUsersMenuEntry.execute();

        verify(consoleService).printUsers(mockUsers);
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
