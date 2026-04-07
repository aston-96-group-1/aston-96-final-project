package ru.aston.hometask.finalproject.providers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.aston.hometask.finalproject.filesystem.FileReader;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FileUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Заполнение списка пользователей из файла json.";

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

    private String readAddress() {
        String imput;
        do {
            System.out.println("Введите путь к JSON‑файлу с пользователями:");
            imput = scanner.nextLine().trim();
        } while (!fileReader.isFileExists(imput));
        return imput;
    }

    @Override
    public List<User> provideUsers(Integer size) {
        try {
            final String jsonFile = fileReader.readFile(readAddress());
            final Type userListType = new TypeToken<List<User>>() {
            }.getType();
            final List<User> users = gson.fromJson(jsonFile, userListType);

            final int sizeJson = users.size();

            if (size < 0) {
                size = sizeJson;
            } else if (size > sizeJson) {
                throw new RuntimeException("Пользователей в файле меньше заданного. Size User: " + sizeJson);
            }

            return users.stream()
                    .map(user -> {
                        final String name = user.getName();
                        final String password = user.getPassword();
                        final String email = user.getEmail();
                        final int postCount = user.getPostCount();
                        if (validator.validate(name, password, email, postCount)) {
                            return User.builder().name(name).email(email).password(password).postCount(postCount).build();
                        }
                        return null;
                    })
                    .limit(size)
                    .collect(Collectors.toList());
        } catch (com.google.gson.JsonSyntaxException e) {
            System.err.println("Ошибка синтаксиса JSON: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
