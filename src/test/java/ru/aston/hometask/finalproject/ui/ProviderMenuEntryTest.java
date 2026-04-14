package ru.aston.hometask.finalproject.ui;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.services.ConsoleService;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProviderMenuEntryTest {
    @Mock
    private ConsoleService consoleService;

    @Mock
    private AppContext appContext;

    @Mock
    private SessionContext sessionContext;

    @Mock
    private IUserProvider mockProvider;

    private ProviderMenuEntry providerMenuEntry;

    @BeforeEach
    void setup() {
        providerMenuEntry = new ProviderMenuEntry(appContext, sessionContext);
    }

    @Test
    void getDescription_ShouldReturnCorrectDescription() {
        assertTrue(providerMenuEntry.getDescription().contains(Strings.PROVIDER_MENU_TITLE.get()));
    }

    @Test
    void getState_WhenNotReady_ShouldReturnNotReadySign() {
        when(sessionContext.isUserProviderReady()).thenReturn(false);

        String state = providerMenuEntry.getState();

        assertEquals(StateSign.NOT_READY.getSign(), state);
    }

    @Test
    void getState_Ready_ShouldReturnReadySign() {
        when(sessionContext.isUserProviderReady()).thenReturn(true);

        String state = providerMenuEntry.getState();

        assertEquals(StateSign.READY.getSign(), state);
    }

    @Test
    void execute_ShouldUpdateSessionContext() {
        when(appContext.getConsoleService()).thenReturn(consoleService);
        Map<String, IUserProvider> mockMap = Map.of("1", mockProvider);
        when(appContext.getProviderMap()).thenReturn(mockMap);

        when(consoleService.getFromMap(anyString(), anyString(), eq(mockMap))).thenReturn(mockProvider);
        when(consoleService.getValidInt(anyString())).thenReturn(100);

        providerMenuEntry.execute();

        verify(sessionContext).setProvider(mockProvider);
        verify(sessionContext).setSize(100);
    }
}
