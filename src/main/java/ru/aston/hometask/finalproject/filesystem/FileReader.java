package ru.aston.hometask.finalproject.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public String readFile(final String userPath) {
        final Path filePath = Paths.get(userPath);

        try {
            if (Files.notExists(filePath) || Files.size(filePath) == 0) {
                return "[]";
            }
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
