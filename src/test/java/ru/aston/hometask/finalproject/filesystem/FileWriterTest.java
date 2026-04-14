package ru.aston.hometask.finalproject.filesystem;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.aston.hometask.finalproject.models.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterTest {
    private FileWriter fileWriter;
    private FileReader fileReader;
    private Gson gson;

    @TempDir
    Path tempDir;

    private Path testFile;

    @BeforeEach
    void setup() {
        gson = new Gson();
        fileWriter = new FileWriter(gson);
        testFile = tempDir.resolve("users.txt");
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

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(2, readUsers.size());
        assertEquals("John Doe", readUsers.getFirst().getName());
        assertEquals("john@example.com", readUsers.getFirst().getEmail());
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

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(2, readUsers.size());
        assertEquals("John", readUsers.getFirst().getName());
        assertEquals("Jane", readUsers.get(1).getName());
    }

    @Test
    void writeToFile_WhenFileExistsWithContent_ShouldMergeUsers() throws IOException {
        List<User> existingUsers = Arrays.asList(
                createUser("Alice", "alice123", "alice@test.com", 10),
                createUser("Bob", "bob456", "bob@test.com", 7)
        );

        fileWriter.writeToFile(testFile.toString(), existingUsers);

        List<User> newUsers = Arrays.asList(
                createUser("Charlie", "charlie789", "charlie@test.com", 2)
        );

        fileWriter.writeToFile(testFile.toString(), newUsers);

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(3, readUsers.size());
        assertEquals("Alice", readUsers.getFirst().getName());
        assertEquals("Bob", readUsers.get(1).getName());
        assertEquals("Charlie", readUsers.get(2).getName());
    }

    @Test
    void writeToFile_WhenFileIsEmpty_ShouldWriteUsers() throws IOException {

        List<User> users = createTestUsers();
        fileWriter.writeToFile(testFile.toString(), users);

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(2, readUsers.size());
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

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(3, readUsers.size());
        assertEquals("User1", readUsers.getFirst().getName());
        assertEquals("User2", readUsers.get(1).getName());
        assertEquals("User3", readUsers.get(2).getName());
    }

    @Test
    void writeToFile_WhenWritingLargeNumberOfUsers_ShouldHandleCorrectly() throws IOException {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            users.add(createUser("User" + i, "pass" + i, "user" + i + "@test.com", i));
        }

        fileWriter.writeToFile(testFile.toString(), users);

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(100, readUsers.size());
        assertEquals("User1", readUsers.getFirst().getName());
        assertEquals("User100", readUsers.getLast().getName());
    }

    @Test
    void writeToFile_WhenInvalidPath_ShouldThrowRuntimeException() {
        Path invalidPath = tempDir.resolve("nonexistent/subdir/users.txt");
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

        List<User> readUsers;

        try (Stream<String> lines = Files.lines(testFile, StandardCharsets.UTF_8)) {
            readUsers = lines
                    .map(line -> gson.fromJson(line, User.class))
                    .collect(Collectors.toList());
        }

        assertNotNull(readUsers);
        assertEquals(2, readUsers.size());
        assertEquals("John", readUsers.getFirst().getName());
        assertEquals("John", readUsers.get(1).getName());
        assertEquals("john@test.com", readUsers.getFirst().getEmail());
        assertEquals("john@test.com", readUsers.get(1).getEmail());
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

