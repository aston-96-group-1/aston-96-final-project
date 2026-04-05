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
                throw new RuntimeException("Файл не найден" + filePath);
            }
            if (!Files.isRegularFile(filePath)) {
                throw new RuntimeException("Ошибка: указанный путь не является файлом: " + filePath);
            } else if (!Files.isReadable(filePath)) {
                throw new RuntimeException("Ошибка: нет прав на чтение файла: " + filePath);
            }
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
