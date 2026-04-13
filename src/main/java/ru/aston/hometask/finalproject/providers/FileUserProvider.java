package ru.aston.hometask.finalproject.providers;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.filesystem.FileReader;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUserProvider implements IUserProvider {
    private final Scanner scanner;
    private final Validator validator;
    private final FileReader fileReader;
    private final Gson gson;

    public FileUserProvider(final Scanner scanner, final Validator validator, FileReader fileReader, Gson gson) {
        this.scanner = scanner;
        this.validator = validator;
        this.fileReader = fileReader;
        this.gson = gson;
    }

    private Path getFilePath() {
        String input;
        while (true) {
            System.out.println(Strings.FILE_PATH_INPUT_PROMPT.get());
            input = scanner.nextLine().trim();

            if (fileReader.isFileExists(input)) {
                return Paths.get(input);
            } else {
                System.out.println(String.format(Strings.ERROR_FILE_NOT_VALID.get(), input));
            }
        }
    }

    @Override
    public List<User> provideUsers(Integer size) {
        Objects.requireNonNull(size, Strings.ERROR_OBJECT_IS_NULL.get());

        List<User> users;
        final Path filePath = getFilePath();

        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            users = lines.filter(line -> !line.isBlank())
                    .map(line -> {
                        try {
                            return gson.fromJson(line, User.class);
                        } catch (JsonSyntaxException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(user -> validator.validate(user.getName(), user.getPassword(), user.getEmail(), user.getPostCount()))
                    .limit(size < 0 ? Long.MAX_VALUE : size)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(Strings.ERROR_FILE_READ_FAILED.get());
        }

        if (size < 0) {
            size = users.size();
        }

        if (size > users.size()) {
            throw new RuntimeException(String.format(Strings.ERROR_FILE_SIZE_LESS_THAN_NEEDED.get(), users.size()));
        }

        return users;
    }

    @Override
    public String getDescription() {
        return Strings.FILE_USER_PROVIDER_TITLE.get();
    }
}
