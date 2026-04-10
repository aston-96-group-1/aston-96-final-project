package ru.aston.hometask.finalproject.providers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.aston.hometask.finalproject.filesystem.FileReader;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

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

    private String getFilePass() {
        String input;
        System.out.println("Введите путь к файлу с пользователями:");

        while (true) {
            input = scanner.nextLine().trim();

            if (fileReader.isFileExists(input)) {
                return input;
            } else {
                System.out.printf("Файл не найден: %s\nПопробуйте ещё раз:%n", input);
            }
        }
    }

    @Override
    public List<User> provideUsers(Integer size) {
        Objects.requireNonNull(size, "Size must not be null");
        if (size < 0) {
            size = Integer.MAX_VALUE;
        }

        final List<User> users = new ArrayList<>();

        Stream<String> lines = fileReader.readFile(getFilePass());
        lines
                .filter(line -> !line.trim().isEmpty())
                .map(line -> gson.fromJson(line, User.class))
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
                .peek(user -> System.out.println("Обработан пользователь: " + user))
                .filter(Objects::nonNull)
                .forEach(users::add);

        if (size > users.size()) {
            throw new RuntimeException(String.format("Пользователей в файле меньше заданного. Size User: %d", users.size()));
        }

        return users;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final FileReader fileReader = new FileReader();
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final Validator validator = new Validator();


        System.out.print("Введите колличество студентов: ");

        int total = scanner.nextInt();
        scanner.nextLine();


        Scanner scanner1 = new Scanner("C:\\Users\\fiash\\OneDrive\\Документы\\HomeWork\\Java\\Aston\\aston-96-final-project\\storage\\users.txt");//System.in);//


        FileUserProvider fileUserProvider = new FileUserProvider(scanner1, validator, fileReader, gson);

        List<User> user = fileUserProvider.provideUsers(total);
        scanner.close();


        user.stream()
                .forEach(System.out::println);


    }
}
