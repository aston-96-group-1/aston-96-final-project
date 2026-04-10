package ru.aston.hometask.finalproject.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {

    public boolean isFileExists(final String userPath) {
        try {
            final Path filePath = Paths.get(userPath);
            return Files.isRegularFile(filePath) && Files.isReadable(filePath);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public Stream<String> readFile(final String userPath) {
        final Path filePath = Paths.get(userPath);

        try {
            if (Files.size(filePath) == 0) {
                return null;
            }
            return Files.lines(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
