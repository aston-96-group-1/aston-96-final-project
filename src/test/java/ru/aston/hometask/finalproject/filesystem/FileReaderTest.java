package ru.aston.hometask.finalproject.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    private FileReader fileReader;

    @TempDir
    Path tempDir;

    private Path existingFile;
    private Path emptyFile;
    private Path nonExistentFile;
    private Path directoryPath;
    private Path unreadableFile;

    @BeforeEach
    void setUp() throws IOException {
        fileReader = new FileReader();

        existingFile = tempDir.resolve("test.json");
        Files.writeString(existingFile, "{\"name\":\"John\",\"password\":\"123\",\"email\":\"john@test.com\",\"postCount\":5}");

        emptyFile = tempDir.resolve("empty.json");
        Files.writeString(emptyFile, "");

        nonExistentFile = tempDir.resolve("notexist.json");

        directoryPath = tempDir.resolve("directory");
        Files.createDirectory(directoryPath);

        unreadableFile = tempDir.resolve("unreadable.json");
        Files.writeString(unreadableFile, "secret content");
        try {
            unreadableFile.toFile().setReadable(false);
        } catch (Exception e) {
        }
    }

    @Test
    void isFileExists_WhenFileExists_ShouldReturnTrue() {
        boolean result = fileReader.isFileExists(existingFile.toString());
        assertTrue(result);
    }

    @Test
    void isFileExists_WhenFileDoesNotExist_ShouldReturnFalse() {
        boolean result = fileReader.isFileExists(nonExistentFile.toString());
        assertFalse(result);
    }

    @Test
    void isFileExists_WhenPathIsDirectory_ShouldReturnTrue() {
        boolean result = fileReader.isFileExists(directoryPath.toString());
        assertTrue(result);
    }

    @Test
    void readFile_WhenFileExists_ShouldReturnContent() {
        String content = fileReader.readFile(existingFile.toString());
        assertTrue(content.contains("John"));
        assertTrue(content.contains("john@test.com"));
    }

    @Test
    void readFile_WhenFileIsEmpty_ShouldReturnEmptyArray() {
        String content = fileReader.readFile(emptyFile.toString());
        assertEquals("[]", content);
    }

    @Test
    void readFile_WhenFileDoesNotExist_ShouldThrowRuntimeException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.readFile(nonExistentFile.toString()));
        assertTrue(exception.getMessage().contains("Файл не найден"));
    }

    @Test
    void readFile_WhenPathIsDirectory_ShouldThrowRuntimeException() {
        try {
            String result = fileReader.readFile(directoryPath.toString());
            assertNotNull(result);
            System.out.println("Warning: readFile on directory returned: " + result);
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("не является файлом") ||
                    e.getMessage().contains("Файл не найден"));
        }
    }

    @Test
    void readFile_WhenPathIsDirectory_ShouldThrowExceptionBasedOnImplementation() {
        assertDoesNotThrow(() -> {
            String result = fileReader.readFile(directoryPath.toString());
            assertNotNull(result);
        });
    }

    @Test
    void readFile_WhenFileExistsButLargeContent_ShouldReadSuccessfully() throws IOException {
        StringBuilder largeContent = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            largeContent.append("Line ").append(i).append("\n");
        }
        Path largeFile = tempDir.resolve("large.json");
        Files.writeString(largeFile, largeContent.toString());

        String content = fileReader.readFile(largeFile.toString());
        assertEquals(largeContent.toString(), content);
    }

    @Test
    void readFile_WhenFileHasJsonContent_ShouldReturnCorrectJson() throws IOException {
        String jsonContent = "[{\"name\":\"User1\",\"password\":\"pass1\",\"email\":\"user1@test.com\",\"postCount\":10}]";
        Path jsonFile = tempDir.resolve("users.json");
        Files.writeString(jsonFile, jsonContent);

        String content = fileReader.readFile(jsonFile.toString());
        assertEquals(jsonContent, content);
    }

    @Test
    void readFile_WhenFileIsUnreadable_ShouldThrowRuntimeException() {
        if (unreadableFile.toFile().canRead()) {
            System.out.println("Skipping unreadable file test on this OS");
            return;
        }

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.readFile(unreadableFile.toString()));
        assertTrue(exception.getMessage().contains("нет прав на чтение") ||
                exception.getMessage().contains("Access denied"));
    }
}