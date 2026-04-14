package ru.aston.hometask.finalproject.filesystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FileReaderTest {
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
    void isFileExists_WhenPathIsDirectory_ShouldReturnFalse() {
        boolean result = fileReader.isFileExists(directoryPath.toString());
        assertFalse(result);
    }
}
