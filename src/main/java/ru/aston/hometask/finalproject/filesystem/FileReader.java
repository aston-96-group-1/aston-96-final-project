package ru.aston.hometask.finalproject.filesystem;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public boolean isFileExists(final String userPath) {
        try {
            final Path filePath = Paths.get(userPath);
            return Files.isRegularFile(filePath) && Files.isReadable(filePath);
        } catch (InvalidPathException e) {
            return false;
        }
    }
}
