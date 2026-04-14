package ru.aston.hometask.finalproject.services;

import io.github.cdimascio.dotenv.Dotenv;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.models.User;

import java.nio.file.Paths;
import java.util.List;

public class LogService {
    private final Dotenv dotenv;
    private final FileWriter fileWriter;

    public LogService(final Dotenv dotenv, final FileWriter fileWriter) {
        this.dotenv = dotenv;
        this.fileWriter = fileWriter;
    }

    public String logFound(final User user, final int count) {
        fileWriter.writeToFile(dotenv.get("OUTPUT"), List.of(user));
        fileWriter.writeToFile(dotenv.get("OUTPUT"), List.of(count));
        return Paths.get(dotenv.get("OUTPUT")).toString();
    }

    public String logSorted(final List<User> users) {
        fileWriter.writeToFile(dotenv.get("OUTPUT"), users);
        return Paths.get(dotenv.get("OUTPUT")).toString();
    }
}
