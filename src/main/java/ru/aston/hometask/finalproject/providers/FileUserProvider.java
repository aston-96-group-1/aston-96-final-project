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

    @Override
    public List<User> provideUsers(Integer size) {
        try {
            String jsonContent = fileReader.readFile(scanner.nextLine().trim());
            Type userListType = new TypeToken<List<User>>() {}.getType();
            List<User> user = gson.fromJson(jsonContent, userListType);

            int sizeJson = user.size();

            if (size < 0){
                size = sizeJson;
            } else if (size > sizeJson) {
                System.out.println("Пользователей в файле меньше заданного. Size User: " + sizeJson);
                return null;
            }

            return user.stream()
                    .filter(name -> validator.isValidName(name.getName()))
                    .filter(email -> validator.isValidEmail(email.getEmail()))
                    .filter(password -> validator.isValidPassword(password.getPassword()))
                    .filter(postCount -> validator.isValidPostCount(postCount.getPostCount()))
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
