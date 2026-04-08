package ru.aston.hometask.finalproject.filesystem;

import com.google.gson.Gson;
import ru.aston.hometask.finalproject.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileWriter {
    private final Gson gson;
    private final FileReader fileReader;

    public FileWriter(final Gson gson, final FileReader fileReader) {
        this.gson = gson;
        this.fileReader = fileReader;
    }

    public void writeToFile(final String userPath, final List<User> users) {
        if (users == null || users.isEmpty()) {
            return;
        }

        final Path filePath = Paths.get(userPath);

        users.forEach(user -> {
            final String jsonUser = gson.toJson(user);
            try {
                Files.writeString(filePath, String.format("%s\n", jsonUser), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
