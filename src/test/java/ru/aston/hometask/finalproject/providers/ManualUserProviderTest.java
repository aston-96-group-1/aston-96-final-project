package ru.aston.hometask.finalproject.providers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManualUserProviderTest {

    @Mock
    private Scanner scanner;

    @Mock
    private Validator validator;

    @InjectMocks
    private ManualUserProvider manualUserProvider;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @BeforeEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void provideUsers_shouldReturnListOfUsers_whenValidInput() {
        int size = 2;

        when(scanner.nextLine())
                .thenReturn("john")
                .thenReturn("pass12345")
                .thenReturn("john@test.com")
                .thenReturn("10")
                .thenReturn("jane")
                .thenReturn("pass67890")
                .thenReturn("jane@test.com")
                .thenReturn("5");

        when(validator.isValidName("john")).thenReturn(true);
        when(validator.isValidName("jane")).thenReturn(true);
        when(validator.isValidPassword("pass12345")).thenReturn(true);
        when(validator.isValidPassword("pass67890")).thenReturn(true);
        when(validator.isValidEmail("john@test.com")).thenReturn(true);
        when(validator.isValidEmail("jane@test.com")).thenReturn(true);
        when(validator.isValidPostCount(10)).thenReturn(true);
        when(validator.isValidPostCount(5)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(2, result.size());

        User firstUser = result.get(0);
        assertEquals("john", firstUser.getName());
        assertEquals("pass12345", firstUser.getPassword());
        assertEquals("john@test.com", firstUser.getEmail());
        assertEquals(10, firstUser.getPostCount());

        User secondUser = result.get(1);
        assertEquals("jane", secondUser.getName());
        assertEquals("pass67890", secondUser.getPassword());
        assertEquals("jane@test.com", secondUser.getEmail());
        assertEquals(5, secondUser.getPostCount());

        String output = outContent.toString();
        assertTrue(output.contains("=== ввод пользователя #1 ==="));
        assertTrue(output.contains("=== ввод пользователя #2 ==="));
        assertTrue(output.contains("Имя:"));
        assertTrue(output.contains("Пароль:"));
        assertTrue(output.contains("Email:"));
        assertTrue(output.contains("Количество постов:"));
    }

    @Test
    void provideUsers_shouldRetryOnInvalidName() {
        int size = 1;

        when(scanner.nextLine())
                .thenReturn("invalid@name")
                .thenReturn("validname")
                .thenReturn("pass12345")
                .thenReturn("test@test.com")
                .thenReturn("5");

        when(validator.isValidName("invalid@name")).thenReturn(false);
        when(validator.isValidName("validname")).thenReturn(true);
        when(validator.isValidPassword("pass12345")).thenReturn(true);
        when(validator.isValidEmail("test@test.com")).thenReturn(true);
        when(validator.isValidPostCount(5)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("validname", result.get(0).getName());

        String output = outContent.toString();
        assertTrue(output.contains("Имя должно содержать только латинские буквы и цифры."));
    }

    @Test
    void provideUsers_shouldRetryOnInvalidPassword() {
        int size = 1;

        when(scanner.nextLine())
                .thenReturn("john")
                .thenReturn("short")
                .thenReturn("validpass123")
                .thenReturn("test@test.com")
                .thenReturn("5");

        when(validator.isValidName("john")).thenReturn(true);
        when(validator.isValidPassword("short")).thenReturn(false);
        when(validator.isValidPassword("validpass123")).thenReturn(true);
        when(validator.isValidEmail("test@test.com")).thenReturn(true);
        when(validator.isValidPostCount(5)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("validpass123", result.get(0).getPassword());

        String output = outContent.toString();
        assertTrue(output.contains("Пароль должен быть от 8 до 20 символов"));
    }

    @Test
    void provideUsers_shouldRetryOnInvalidEmail() {
        int size = 1;

        when(scanner.nextLine())
                .thenReturn("john")
                .thenReturn("pass12345")
                .thenReturn("invalid-email")
                .thenReturn("valid@test.com")
                .thenReturn("5");

        when(validator.isValidName("john")).thenReturn(true);
        when(validator.isValidPassword("pass12345")).thenReturn(true);
        when(validator.isValidEmail("invalid-email")).thenReturn(false);
        when(validator.isValidEmail("valid@test.com")).thenReturn(true);
        when(validator.isValidPostCount(5)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("valid@test.com", result.get(0).getEmail());

        String output = outContent.toString();
        assertTrue(output.contains("Некорректный email."));
    }

    @Test
    void provideUsers_shouldRetryOnInvalidPostCount() {
        int size = 1;

        when(scanner.nextLine())
                .thenReturn("john")
                .thenReturn("pass12345")
                .thenReturn("test@test.com")
                .thenReturn("-5")
                .thenReturn("300")
                .thenReturn("10");

        when(validator.isValidName("john")).thenReturn(true);
        when(validator.isValidPassword("pass12345")).thenReturn(true);
        when(validator.isValidEmail("test@test.com")).thenReturn(true);
        when(validator.isValidPostCount(-5)).thenReturn(false);
        when(validator.isValidPostCount(300)).thenReturn(false);
        when(validator.isValidPostCount(10)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getPostCount());

        String output = outContent.toString();
        assertTrue(output.contains("Введите число от от 0 до 255"));
    }

    @Test
    void provideUsers_shouldRetryOnNumberFormatExceptionForPostCount() {
        int size = 1;

        when(scanner.nextLine())
                .thenReturn("john")
                .thenReturn("pass12345")
                .thenReturn("test@test.com")
                .thenReturn("not a number")
                .thenReturn("10");

        when(validator.isValidName("john")).thenReturn(true);
        when(validator.isValidPassword("pass12345")).thenReturn(true);
        when(validator.isValidEmail("test@test.com")).thenReturn(true);
        when(validator.isValidPostCount(10)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(10, result.get(0).getPostCount());

        String output = outContent.toString();
        assertTrue(output.contains("Введите число от от 0 до 255"));
    }

    @Test
    void provideUsers_shouldThrowNullPointerException_whenSizeIsNull() {
        assertThrows(NullPointerException.class, () -> manualUserProvider.provideUsers(null));
    }

    @Test
    void provideUsers_shouldThrowIllegalArgumentException_whenSizeIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> manualUserProvider.provideUsers(-5)
        );
        assertEquals("Размер списка не может быть отрицательным!", exception.getMessage());
    }

    @Test
    void provideUsers_shouldHandleSizeZero() {
        List<User> result = manualUserProvider.provideUsers(0);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(scanner, never()).nextLine();
    }

    @Test
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Ручной ввод пользователей.", manualUserProvider.getDescription());
    }

    @Test
    void provideUsers_shouldShowCorrectUserNumberInPrompt() {
        int size = 3;

        when(scanner.nextLine())
                .thenReturn("user1").thenReturn("pass12345").thenReturn("u1@test.com").thenReturn("1")
                .thenReturn("user2").thenReturn("pass12345").thenReturn("u2@test.com").thenReturn("2")
                .thenReturn("user3").thenReturn("pass12345").thenReturn("u3@test.com").thenReturn("3");

        when(validator.isValidName(anyString())).thenReturn(true);
        when(validator.isValidPassword(anyString())).thenReturn(true);
        when(validator.isValidEmail(anyString())).thenReturn(true);
        when(validator.isValidPostCount(anyInt())).thenReturn(true);

        manualUserProvider.provideUsers(size);

        String output = outContent.toString();
        assertTrue(output.contains("=== ввод пользователя #1 ==="));
        assertTrue(output.contains("=== ввод пользователя #2 ==="));
        assertTrue(output.contains("=== ввод пользователя #3 ==="));
    }

    @Test
    void provideUsers_shouldValidateAllFieldsForEachUser() {
        int size = 2;

        when(scanner.nextLine())
                .thenReturn("alice").thenReturn("alice12345").thenReturn("alice@test.com").thenReturn("15")
                .thenReturn("bob").thenReturn("bob67890").thenReturn("bob@test.com").thenReturn("20");

        when(validator.isValidName("alice")).thenReturn(true);
        when(validator.isValidName("bob")).thenReturn(true);
        when(validator.isValidPassword("alice12345")).thenReturn(true);
        when(validator.isValidPassword("bob67890")).thenReturn(true);
        when(validator.isValidEmail("alice@test.com")).thenReturn(true);
        when(validator.isValidEmail("bob@test.com")).thenReturn(true);
        when(validator.isValidPostCount(15)).thenReturn(true);
        when(validator.isValidPostCount(20)).thenReturn(true);

        List<User> result = manualUserProvider.provideUsers(size);

        assertEquals(2, result.size());

        verify(validator).isValidName("alice");
        verify(validator).isValidPassword("alice12345");
        verify(validator).isValidEmail("alice@test.com");
        verify(validator).isValidPostCount(15);

        verify(validator).isValidName("bob");
        verify(validator).isValidPassword("bob67890");
        verify(validator).isValidEmail("bob@test.com");
        verify(validator).isValidPostCount(20);
    }
}