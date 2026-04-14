package ru.aston.hometask.finalproject.context;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.providers.IUserProvider;
import ru.aston.hometask.finalproject.services.ConsoleService;
import ru.aston.hometask.finalproject.services.LogService;
import ru.aston.hometask.finalproject.sorting.Sort;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AppContextTest {
    @Mock
    Scanner scanner;

    @Mock
    Map<String, IUserProvider> providerMap;

    @Mock
    Map<String, Sort> sortMap;

    @Mock
    ConsoleService consoleService;

    @Mock
    LogService logService;

    @Test
    void build_WhenAllFieldsSupplied_ShouldCreateAppContext() {
        AppContext appContext = AppContext.builder()
                .scanner(scanner)
                .providerMap(providerMap)
                .sortMap(sortMap)
                .consoleService(consoleService)
                .logService(logService)
                .build();

        assertNotNull(appContext);
    }

    @Test
    void build_WhenFieldsMissing_ShouldThrowException() {
        AppContext.Builder builder = AppContext.builder().scanner(scanner);
        assertThrows(NullPointerException.class, builder::build);
    }
}
