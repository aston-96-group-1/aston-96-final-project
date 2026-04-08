package ru.aston.hometask.finalproject.providers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.filesystem.FileReader;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileUserProviderTest {

    @Mock
    private Scanner scanner;

    @Mock
    private Validator validator;

    @Mock
    private FileReader fileReader;

    @Mock
    private Gson gson;

    @InjectMocks
    private FileUserProvider fileUserProvider;

    private final String validJson = "[{\"name\":\"john\",\"password\":\"password123\",\"email\":\"john@example.com\",\"postCount\":10}]";
    private final Type userListType = new TypeToken<List<User>>() {}.getType();

    @Test
    void provideUsers_shouldReturnValidUsers_whenAllDataIsValid() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        List<User> expectedUsers = List.of(
                User.builder().name("john").password("password123").email("john@example.com").postCount(10).build()
        );

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(expectedUsers);
        when(validator.validate("john", "password123", "john@example.com", 10)).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getName());
        assertEquals("password123", result.get(0).getPassword());
        assertEquals("john@example.com", result.get(0).getEmail());
        assertEquals(10, result.get(0).getPostCount());

        verify(validator).validate("john", "password123", "john@example.com", 10);
    }

    @Test
    void provideUsers_shouldLimitUsersBySize_whenSizeLessThanFileSize() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        List<User> allUsers = Arrays.asList(
                User.builder().name("user1").password("pass1").email("u1@test.com").postCount(1).build(),
                User.builder().name("user2").password("pass2").email("u2@test.com").postCount(2).build()
        );

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(allUsers);
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(1);

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getName());
    }

    @Test
    void provideUsers_shouldReturnAllUsers_whenSizeIsNegative() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        List<User> allUsers = Arrays.asList(
                User.builder().name("user1").password("pass1").email("u1@test.com").postCount(1).build(),
                User.builder().name("user2").password("pass2").email("u2@test.com").postCount(2).build()
        );

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(allUsers);
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(-5);

        assertEquals(2, result.size());
    }

    @Test
    void provideUsers_shouldThrowException_whenSizeExceedsFileUsersCount() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        List<User> allUsers = Arrays.asList(
                User.builder().name("user1").password("pass1").email("u1@test.com").postCount(1).build(),
                User.builder().name("user2").password("pass2").email("u2@test.com").postCount(2).build()
        );

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(allUsers);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileUserProvider.provideUsers(10));

        assertTrue(exception.getMessage().contains("Пользователей в файле меньше заданного"));
    }

    @Test
    void provideUsers_shouldFilterOutInvalidUsers() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        User validUser = User.builder().name("valid").password("pass123").email("valid@test.com").postCount(5).build();
        User invalidUser = User.builder().name("inv@lid").password("pass").email("bad").postCount(-1).build();

        List<User> allUsers = Arrays.asList(validUser, invalidUser);

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(allUsers);
        when(validator.validate("valid", "pass123", "valid@test.com", 5)).thenReturn(true);
        List<User> result = fileUserProvider.provideUsers(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("valid", result.get(0).getName());

        verify(validator, times(1)).validate(anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    void provideUsers_shouldReturnNull_whenJsonSyntaxException() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn("invalid json");
        when(gson.fromJson(eq("invalid json"), eq(userListType))).thenThrow(JsonSyntaxException.class);

        List<User> result = fileUserProvider.provideUsers(5);

        assertNull(result);
    }

    @Test
    void provideUsers_shouldPropagateRuntimeException_whenFileReaderThrows() {
        when(scanner.nextLine()).thenReturn("missing.json");
        when(fileReader.readFile("missing.json")).thenThrow(new RuntimeException("Файл не найден"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileUserProvider.provideUsers(5));

        assertEquals("Файл не найден", exception.getMessage());
    }

    @Test
    void provideUsers_shouldReturnEmptyList_whenAllUsersInvalid() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        User invalidUser1 = User.builder().name("bad1").password("bad").email("bad1").postCount(-5).build();

        List<User> users = Arrays.asList(invalidUser1);

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(users);
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(false);

        List<User> result = fileUserProvider.provideUsers(1);

        assertNotNull(result);
        assertTrue(result.isEmpty() || result.stream().allMatch(user -> user == null));
    }

    @Test
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Заполнение списка пользователей из файла json.", fileUserProvider.getDescription());
    }

    @Test
    void provideUsers_shouldHandleZeroSize() {
        when(scanner.nextLine()).thenReturn("users.json");
        when(fileReader.readFile("users.json")).thenReturn(validJson);

        List<User> expectedUsers = List.of(
                User.builder().name("john").password("password123").email("john@example.com").postCount(10).build()
        );

        when(gson.fromJson(eq(validJson), eq(userListType))).thenReturn(expectedUsers);

        List<User> result = fileUserProvider.provideUsers(0);

        assertNotNull(result);
        assertEquals(0, result.size());
    }
}