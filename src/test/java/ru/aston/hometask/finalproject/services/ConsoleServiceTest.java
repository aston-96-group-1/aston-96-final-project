package ru.aston.hometask.finalproject.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.common.IDescribable;
import ru.aston.hometask.finalproject.constants.Strings;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConsoleServiceTest {
    private ConsoleService consoleService;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Mock
    private Scanner scanner;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outContent));
        consoleService = new ConsoleService(scanner);
    }

    @Test
    void getFromMap_WhenOptionIsPresent_ShouldReturnValidOption() {
        when(scanner.nextLine()).thenReturn("1");
        var res = consoleService.getFromMap("Test", "Error", createTestMap());
        assertEquals("Test Description", res.getDescription());
    }

    @Test
    void getFromMap_WhenOptionIsNotPresent_ShouldOutPutErrorMessage() {
        when(scanner.nextLine()).thenReturn("0", "1");

        consoleService.getFromMap("Test", "Error", createTestMap());

        assertTrue(outContent.toString().contains("Error"));
    }

    @Test
    void getValidInt_WhenInputIsNumber_ShouldReturnValidInt() {
        when(scanner.nextLine()).thenReturn("42");

        int result = consoleService.getValidInt("Enter number");

        assertEquals(42, result);
    }

    @Test
    void getValidInt_WhenInputIsNotNumber_ShouldOutPutErrorMessage() {
        when(scanner.nextLine()).thenReturn("abc", "10");

        consoleService.getValidInt("Enter number");

        assertTrue(outContent.toString().contains(Strings.ERROR_INPUT_MUST_BE_NUMBER.get()));
    }

    @Test
    void printError_ShouldOutPutErrorMessage() {
        consoleService.printError("Critical Error");

        assertEquals("Critical Error" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void waitForEnter_ShouldOutPutMessageAndAcceptInput() {
        when(scanner.nextLine()).thenReturn("");

        consoleService.waitForEnter();

        assertTrue(outContent.toString().contains(Strings.PRESS_ENTER.get()));
    }

    private static class TestDescribable implements IDescribable {
        @Override
        public String getDescription() {
            return "Test Description";
        }
    }

    private Map<String, IDescribable> createTestMap() {
        Map<String, IDescribable> map = new LinkedHashMap<>();
        map.put("1", new TestDescribable());
        return map;
    }
}
