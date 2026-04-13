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
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.services.ConsoleService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoadUsersMenuEntryTest {
    @Mock
    private ConsoleService consoleService;

    @Mock
    private AppContext appContext;

    @Mock
    private SessionContext sessionContext;

    @Mock
    private IUserProvider mockProvider;

    private LoadUsersMenuEntry loadUsersMenuEntry;

    @BeforeEach
    void setup() {
        loadUsersMenuEntry = new LoadUsersMenuEntry(appContext, sessionContext);
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertTrue(loadUsersMenuEntry.getDescription().contains(Strings.LOAD_USERS_MENU_TITLE.get()));
    }

    @Test
    void getState_WhenNotReady_ShouldReturnNotReadySign() {
        when(sessionContext.isLoadUsersReady()).thenReturn(false);

        String state = loadUsersMenuEntry.getState();

        assertEquals(StateSign.NOT_READY.getSign(), state);
    }

    @Test
    void getState_Ready_ShouldReturnReadySign() {
        when(sessionContext.isLoadUsersReady()).thenReturn(true);

        String state = loadUsersMenuEntry.getState();

        assertEquals(StateSign.READY.getSign(), state);
    }

    @Test
    void execute_WhenNotReady_ShouldShowErrorAndAndWait() {
        when(sessionContext.isUserProviderReady()).thenReturn(false);
        when(appContext.getConsoleService()).thenReturn(consoleService);

        loadUsersMenuEntry.execute();

        verify(consoleService).printError(Strings.ERROR_PROVIDER_AND_SIZE.get());
        verify(consoleService).waitForEnter();
    }

    @Test
    void execute_WhenReady_ShouldSetUsers() {
        when(sessionContext.isUserProviderReady()).thenReturn(true);
        when(sessionContext.getProvider()).thenReturn(mockProvider);
        when(sessionContext.getSize()).thenReturn(2);
        when(mockProvider.provideUsers(2)).thenReturn(createTestUsers());

        loadUsersMenuEntry.execute();

        assertNotNull(sessionContext.getUsers());
    }

    @Test
    void execute_WhenReadySizeNotSupported_ShouldUnsetProviderAndSizePrintErrorAndWait() {
        when(sessionContext.isUserProviderReady()).thenReturn(true);
        when(sessionContext.getProvider()).thenReturn(mockProvider);
        when(sessionContext.getSize()).thenReturn(-1);
        when(appContext.getConsoleService()).thenReturn(consoleService);

        when(mockProvider.provideUsers(-1)).thenThrow(new IllegalArgumentException());

        loadUsersMenuEntry.execute();

        verify(sessionContext).setProvider(null);
        verify(sessionContext).setSize(null);

        verify(consoleService).printError(anyString());
        verify(consoleService).waitForEnter();
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
