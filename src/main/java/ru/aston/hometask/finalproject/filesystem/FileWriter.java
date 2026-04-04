package ru.aston.hometask.finalproject.filesystem;

import com.google.gson.Gson;
import ru.aston.hometask.finalproject.models.User;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileWriter {
    private final Gson gson;
    private final FileReader fileReader;

    public FileWriter(final Gson gson, final FileReader fileReader) {
        this.gson = gson;
        this.fileReader = fileReader;
    }

    public void writeToFile(final String userPath, final List<User> usersNew) {
        if (usersNew == null || usersNew.isEmpty()) {
            return;
        }

        final Path filePath = Paths.get(userPath);
        List<User> users = new ArrayList<>();
        final String json = fileReader.readFile(userPath);
        final User[] userArray = gson.fromJson(json, User[].class);

        if (userArray != null) {
            users.addAll(Arrays.asList(userArray));
        }

        users.addAll(usersNew);
        final String jsonNew = gson.toJson(users);

        try {
            Files.writeString(filePath, jsonNew, StandardOpenOption.CREATE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
