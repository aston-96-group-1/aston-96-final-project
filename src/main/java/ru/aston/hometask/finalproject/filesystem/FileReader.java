package ru.aston.hometask.finalproject.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    public static final String PATH_PATTERN = "^([a-zA-Z]:)?(?:[/\\\\](?:[\\w\\-а-яА-ЯёЁ\\s]+(?:\\.[\\w]+)?)?)+[/\\\\]?$";

    public boolean isFileExists(final String userPath) {
        if (!userPath.matches(PATH_PATTERN)) {
            System.out.println("Некорректный ввод" + userPath);
            return false;
        }

        final Path filePath = Paths.get(userPath);

        if (!userPath.toLowerCase().endsWith("json")) {
            if (Files.notExists(filePath)) {
                System.out.println("Файл не найден: " + filePath);
                return false;
            }
            System.out.println("Файл не является формата JSON.");
            return false;
        }
        if (!Files.isReadable(filePath)) {
            System.out.println("Ошибка: нет прав на чтение файла: " + filePath);
            return false;
        }
        return true;
    }

    public String readFile(final String userPath) {
        final Path filePath = Paths.get(userPath);

        try {
            if (Files.size(filePath) == 0) {
                return "[]";
            }
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
