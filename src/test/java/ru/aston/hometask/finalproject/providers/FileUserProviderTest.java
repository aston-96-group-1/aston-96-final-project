package ru.aston.hometask.finalproject.providers;

import com.google.gson.Gson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.filesystem.FileReader;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private final Gson realGson = new Gson();
    private FileUserProvider fileUserProvider;

    @TempDir
    Path tempDir;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Path testFile;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        testFile = tempDir.resolve("users.json");
        fileUserProvider = new FileUserProvider(scanner, validator, fileReader, realGson);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void provideUsers_shouldReturnListOfUsers_whenValidFileAndValidSize() throws Exception {
        int size = 2;
        String jsonLine1 = "{\"name\":\"john\",\"password\":\"pass12345\",\"email\":\"john@test.com\",\"postCount\":10}";
        String jsonLine2 = "{\"name\":\"jane\",\"password\":\"pass67890\",\"email\":\"jane@test.com\",\"postCount\":5}";
        Files.writeString(testFile, jsonLine1 + "\n" + jsonLine2);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(eq("john"), eq("pass12345"), eq("john@test.com"), eq(10))).thenReturn(true);
        when(validator.validate(eq("jane"), eq("pass67890"), eq("jane@test.com"), eq(5))).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("john", result.get(0).getName());
        assertEquals("jane", result.get(1).getName());
    }

    @Test
    void provideUsers_shouldThrowException_whenNotEnoughValidUsers() throws Exception {
        int size = 2;
        String validJson = "{\"name\":\"valid\",\"password\":\"validpass123\",\"email\":\"valid@test.com\",\"postCount\":10}";
        String invalidJson = "{\"name\":\"invalid@\",\"password\":\"short\",\"email\":\"bad\",\"postCount\":-1}";
        Files.writeString(testFile, validJson + "\n" + invalidJson);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(eq("valid"), eq("validpass123"), eq("valid@test.com"), eq(10))).thenReturn(true);
        when(validator.validate(eq("invalid@"), eq("short"), eq("bad"), eq(-1))).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileUserProvider.provideUsers(size));

        assertTrue(exception.getMessage().contains("Пользователей в файле меньше заданного"));
        assertTrue(exception.getMessage().contains("Size User: 1"));
    }

    @Test
    void provideUsers_shouldSkipLinesWithJsonSyntaxException() throws Exception {
        int size = 1;
        String validJson = "{\"name\":\"john\",\"password\":\"pass12345\",\"email\":\"john@test.com\",\"postCount\":10}";
        String invalidJson = "invalid json string";
        Files.writeString(testFile, validJson + "\n" + invalidJson);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(eq("john"), eq("pass12345"), eq("john@test.com"), eq(10))).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getName());
    }

    @Test
    void provideUsers_shouldSkipBlankLines() throws Exception {
        int size = 1;
        String jsonLine = "{\"name\":\"john\",\"password\":\"pass12345\",\"email\":\"john@test.com\",\"postCount\":10}";
        Files.writeString(testFile, "\n\n" + jsonLine + "\n\n");

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(eq("john"), eq("pass12345"), eq("john@test.com"), eq(10))).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getName());
    }

    @Test
    void provideUsers_shouldRespectSizeLimit_whenSizeLessThanAvailableUsers() throws Exception {
        int size = 2;
        String jsonLine1 = "{\"name\":\"user1\",\"password\":\"pass1\",\"email\":\"u1@test.com\",\"postCount\":1}";
        String jsonLine2 = "{\"name\":\"user2\",\"password\":\"pass2\",\"email\":\"u2@test.com\",\"postCount\":2}";
        String jsonLine3 = "{\"name\":\"user3\",\"password\":\"pass3\",\"email\":\"u3@test.com\",\"postCount\":3}";
        Files.writeString(testFile, jsonLine1 + "\n" + jsonLine2 + "\n" + jsonLine3);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getName());
        assertEquals("user2", result.get(1).getName());
    }

    @Test
    void provideUsers_shouldReturnAllUsers_whenSizeIsNegative() throws Exception {
        int size = -1;
        String jsonLine1 = "{\"name\":\"user1\",\"password\":\"pass1\",\"email\":\"u1@test.com\",\"postCount\":1}";
        String jsonLine2 = "{\"name\":\"user2\",\"password\":\"pass2\",\"email\":\"u2@test.com\",\"postCount\":2}";
        Files.writeString(testFile, jsonLine1 + "\n" + jsonLine2);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(2, result.size());
    }

    @Test
    void provideUsers_shouldThrowRuntimeException_whenNotEnoughUsersInFile() throws Exception {
        int size = 5;
        String jsonLine = "{\"name\":\"user1\",\"password\":\"pass1\",\"email\":\"u1@test.com\",\"postCount\":1}";
        Files.writeString(testFile, jsonLine);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileUserProvider.provideUsers(size));
        assertTrue(exception.getMessage().contains("Пользователей в файле меньше заданного"));
        assertTrue(exception.getMessage().contains("Size User: 1"));
    }

    @Test
    void provideUsers_shouldRetryFilePath_whenFileDoesNotExist() throws Exception {
        String invalidPath = "/invalid/path/file.json";
        String validPath = testFile.toString();

        when(scanner.nextLine()).thenReturn(invalidPath).thenReturn(validPath);
        when(fileReader.isFileExists(invalidPath)).thenReturn(false);
        when(fileReader.isFileExists(validPath)).thenReturn(true);

        Files.writeString(testFile, "{\"name\":\"test\",\"password\":\"pass12345\",\"email\":\"test@test.com\",\"postCount\":1}");
        when(validator.validate(anyString(), anyString(), anyString(), anyInt())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(1);

        assertNotNull(result);
        assertEquals(1, result.size());

        String output = outContent.toString();
        assertTrue(output.contains("Файл не найден: " + invalidPath));
        assertTrue(output.contains("Попробуйте ещё раз:"));
    }

    @Test
    void provideUsers_shouldThrowNullPointerException_whenSizeIsNull() {
        assertThrows(NullPointerException.class, () -> fileUserProvider.provideUsers(null));
    }

    @Test
    void provideUsers_shouldThrowRuntimeException_whenIOExceptionOccurs() {
        String filePath = "/nonexistent/directory/file.json";

        when(scanner.nextLine()).thenReturn(filePath);
        when(fileReader.isFileExists(filePath)).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileUserProvider.provideUsers(5));
        assertEquals("Ошибка чтения файла!", exception.getMessage());
    }

    @Test
    void getDescription_shouldReturnCorrectDescription() {
        assertEquals("Заполнение списка пользователей из файла json.", fileUserProvider.getDescription());
    }

    @Test
    void provideUsers_shouldHandleEmptyFile() throws Exception {
        int size = 1;
        Files.writeString(testFile, "");

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileUserProvider.provideUsers(size));
        assertTrue(exception.getMessage().contains("Пользователей в файле меньше заданного"));
        assertTrue(exception.getMessage().contains("Size User: 0"));
    }

    @Test
    void provideUsers_shouldHandleFileWithOnlyBlankLines() throws Exception {
        int size = 1;
        Files.writeString(testFile, "\n\n\n\n\n");

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> fileUserProvider.provideUsers(size));
        assertTrue(exception.getMessage().contains("Size User: 0"));
    }

    @Test
    void provideUsers_shouldFilterOutUsersFailingValidation() throws Exception {
        int size = 2;
        String jsonLine1 = "{\"name\":\"valid1\",\"password\":\"pass123\",\"email\":\"v1@test.com\",\"postCount\":5}";
        String jsonLine2 = "{\"name\":\"invalid\",\"password\":\"short\",\"email\":\"bad\",\"postCount\":-1}";
        String jsonLine3 = "{\"name\":\"valid2\",\"password\":\"pass456\",\"email\":\"v2@test.com\",\"postCount\":10}";
        Files.writeString(testFile, jsonLine1 + "\n" + jsonLine2 + "\n" + jsonLine3);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(eq("valid1"), eq("pass123"), eq("v1@test.com"), eq(5))).thenReturn(true);
        when(validator.validate(eq("invalid"), eq("short"), eq("bad"), eq(-1))).thenReturn(false);
        when(validator.validate(eq("valid2"), eq("pass456"), eq("v2@test.com"), eq(10))).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(2, result.size());
        assertEquals("valid1", result.get(0).getName());
        assertEquals("valid2", result.get(1).getName());
    }

    @Test
    void provideUsers_shouldHandleSizeZero() throws Exception {
        int size = 0;
        String jsonLine = "{\"name\":\"john\",\"password\":\"pass12345\",\"email\":\"john@test.com\",\"postCount\":10}";
        Files.writeString(testFile, jsonLine);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(0, result.size());
    }

    @Test
    void provideUsers_shouldHandleMultipleInvalidJsonInRow() throws Exception {
        int size = 1;
        String validJson = "{\"name\":\"valid\",\"password\":\"pass12345\",\"email\":\"valid@test.com\",\"postCount\":10}";
        Files.writeString(testFile, "not json\nalso not json\n" + validJson + "\ninvalid again");

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);
        when(validator.validate(eq("valid"), eq("pass12345"), eq("valid@test.com"), eq(10))).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(1, result.size());
        assertEquals("valid", result.get(0).getName());
    }

    @Test
    void provideUsers_shouldReturnEmptyList_whenNoValidUsersAndSizeZero() throws Exception {
        int size = 0;
        String invalidJson1 = "invalid json";
        String invalidJson2 = "also invalid";
        Files.writeString(testFile, invalidJson1 + "\n" + invalidJson2);

        when(scanner.nextLine()).thenReturn(testFile.toString());
        when(fileReader.isFileExists(testFile.toString())).thenReturn(true);

        List<User> result = fileUserProvider.provideUsers(size);

        assertEquals(0, result.size());
    }
}