package ru.aston.hometask.finalproject.filesystem;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.aston.hometask.finalproject.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    private FileWriter fileWriter;
    private Gson gson;
    private FileReader fileReader;

    @TempDir
    Path tempDir;

    private Path testFile;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        fileReader = new FileReader();
        fileWriter = new FileWriter(gson, fileReader);
        testFile = tempDir.resolve("users.json");
    }

    @Test
    void writeToFile_WhenUsersNewIsNull_ShouldDoNothing() throws IOException {
        fileWriter.writeToFile(testFile.toString(), null);

        assertFalse(Files.exists(testFile));
    }

    @Test
    void writeToFile_WhenUsersNewIsEmpty_ShouldDoNothing() throws IOException {
        fileWriter.writeToFile(testFile.toString(), new ArrayList<>());

        assertFalse(Files.exists(testFile));
    }

    @Test
    void writeToFile_WhenFileDoesNotExist_ShouldCreateFileAndWriteUsers() throws IOException {
        List<User> users = createTestUsers();

        fileWriter.writeToFile(testFile.toString(), users);

        assertTrue(Files.exists(testFile));
        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(2, readUsers.length);
        assertEquals("John Doe", readUsers[0].getName());
        assertEquals("john@example.com", readUsers[0].getEmail());
    }

    @Test
    void writeToFile_WhenFileExists_ShouldAppendUsersToExisting() throws IOException {
        List<User> initialUsers = Arrays.asList(
                createUser("John", "pass123", "john@test.com", 5)
        );
        fileWriter.writeToFile(testFile.toString(), initialUsers);

        List<User> newUsers = Arrays.asList(
                createUser("Jane", "pass456", "jane@test.com", 3)
        );
        fileWriter.writeToFile(testFile.toString(), newUsers);

        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(2, readUsers.length);
        assertEquals("John", readUsers[0].getName());
        assertEquals("Jane", readUsers[1].getName());
    }

    @Test
    void writeToFile_WhenFileExistsWithContent_ShouldMergeUsers() throws IOException {
        List<User> existingUsers = Arrays.asList(
                createUser("Alice", "alice123", "alice@test.com", 10),
                createUser("Bob", "bob456", "bob@test.com", 7)
        );
        String initialJson = gson.toJson(existingUsers);
        Files.writeString(testFile, initialJson);

        List<User> newUsers = Arrays.asList(
                createUser("Charlie", "charlie789", "charlie@test.com", 2)
        );
        fileWriter.writeToFile(testFile.toString(), newUsers);

        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(3, readUsers.length);
        assertEquals("Alice", readUsers[0].getName());
        assertEquals("Bob", readUsers[1].getName());
        assertEquals("Charlie", readUsers[2].getName());
    }

    @Test
    void writeToFile_WhenFileIsEmpty_ShouldWriteUsers() throws IOException {
        Files.writeString(testFile, "[]");

        List<User> users = createTestUsers();
        fileWriter.writeToFile(testFile.toString(), users);

        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(2, readUsers.length);
    }

    @Test
    void writeToFile_WhenMultipleWrites_ShouldAccumulateUsers() throws IOException {
        fileWriter.writeToFile(testFile.toString(), Arrays.asList(
                createUser("User1", "pass1", "user1@test.com", 1)
        ));

        fileWriter.writeToFile(testFile.toString(), Arrays.asList(
                createUser("User2", "pass2", "user2@test.com", 2)
        ));

        fileWriter.writeToFile(testFile.toString(), Arrays.asList(
                createUser("User3", "pass3", "user3@test.com", 3)
        ));

        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(3, readUsers.length);
        assertEquals("User1", readUsers[0].getName());
        assertEquals("User2", readUsers[1].getName());
        assertEquals("User3", readUsers[2].getName());
    }

    @Test
    void writeToFile_WhenWritingLargeNumberOfUsers_ShouldHandleCorrectly() throws IOException {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            users.add(createUser("User" + i, "pass" + i, "user" + i + "@test.com", i));
        }

        fileWriter.writeToFile(testFile.toString(), users);

        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(100, readUsers.length);
        assertEquals("User1", readUsers[0].getName());
        assertEquals("User100", readUsers[99].getName());
    }

    @Test
    void writeToFile_WhenInvalidPath_ShouldThrowRuntimeException() {
        Path invalidPath = tempDir.resolve("nonexistent/subdir/users.json");
        List<User> users = createTestUsers();

        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(invalidPath.toString(), users));
    }

    @Test
    void writeToFile_WhenFileIsDirectory_ShouldThrowException() throws IOException {
        Path directory = tempDir.resolve("directory");
        Files.createDirectory(directory);
        List<User> users = createTestUsers();

        assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(directory.toString(), users));
    }

    @Test
    void writeToFile_WithDuplicateUsers_ShouldAppendAll() throws IOException {
        User duplicateUser = createUser("John", "pass123", "john@test.com", 5);

        fileWriter.writeToFile(testFile.toString(), Arrays.asList(duplicateUser));

        fileWriter.writeToFile(testFile.toString(), Arrays.asList(duplicateUser));

        String content = Files.readString(testFile);
        User[] readUsers = gson.fromJson(content, User[].class);

        assertNotNull(readUsers);
        assertEquals(2, readUsers.length);
        assertEquals("John", readUsers[0].getName());
        assertEquals("John", readUsers[1].getName());
        assertEquals("john@test.com", readUsers[0].getEmail());
        assertEquals("john@test.com", readUsers[1].getEmail());
    }

    @Test
    void writeToFile_WhenFileHasInvalidJson_ShouldThrowRuntimeException() throws IOException {
        Files.writeString(testFile, "invalid json content {");

        List<User> users = createTestUsers();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(testFile.toString(), users));

        String errorMessage = exception.getMessage();
        assertTrue(errorMessage.contains("JsonSyntaxException") ||
                        errorMessage.contains("BEGIN_ARRAY") ||
                        errorMessage.contains("Expected BEGIN_ARRAY"),
                "Error message should contain JSON parsing error");

        String content = Files.readString(testFile);
        assertEquals("invalid json content {", content);
    }

    @Test
    void writeToFile_WhenFileHasInvalidJson_ShouldThrowExceptionWithCorrectCause() throws IOException {
        Files.writeString(testFile, "{invalid json}");

        List<User> users = createTestUsers();

        Exception exception = assertThrows(Exception.class,
                () -> fileWriter.writeToFile(testFile.toString(), users));

        assertTrue(exception instanceof RuntimeException);

        String content = Files.readString(testFile);
        assertEquals("{invalid json}", content);
    }

    @Test
    void writeToFile_WhenFileHasValidButWrongJsonStructure_ShouldThrowRuntimeException() throws IOException {
        Files.writeString(testFile, "{\"name\":\"test\"}");

        List<User> users = createTestUsers();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.writeToFile(testFile.toString(), users));

        assertNotNull(exception);

        String content = Files.readString(testFile);
        assertEquals("{\"name\":\"test\"}", content);
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