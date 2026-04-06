package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class ManualUserProvider implements IUserProvider {
    public static final String DESCRIPTION = "Ручной ввод пользователей.";

    private final Scanner scanner;
    private final Validator validator;

    public ManualUserProvider(final Scanner scanner, final Validator validator) {
        this.scanner = scanner;
        this.validator = validator;
    }

    private String readValidated(
            String prompt,
            Predicate<String> validatorFn,
            String errorMessage
    ) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (validatorFn.test(input)) {
                return input;
            }

            System.out.println(errorMessage);
        }
    }

    private int readValidatedInt(
            String prompt,
            IntPredicate validatorFn,
            String errorMessage
    ) {
        while (true) {
            System.out.print(prompt);

            try {
                int value = Integer.parseInt(scanner.nextLine());

                if (validatorFn.test(value)) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }

            System.out.println(errorMessage);
        }
    }

    private String readName() {
        return readValidated(
                "Имя: ",
                validator::isValidName,
                "Имя должно содержать только латинские буквы и цифры."
        );
    }

    private String readPassword() {
        return readValidated(
                "Пароль: ",
                validator::isValidPassword,
                String.format("Пароль должен быть от %s до %s символов",
                        Validator.PASSWORD_MIN_LENGTH,
                        Validator.PASSWORD_MAX_LENGTH)
        );
    }

    private String readEmail() {
        return readValidated(
                "Email: ",
                validator::isValidEmail,
                "Некорректный email."
        );
    }

    private int readPostCount() {
        return readValidatedInt(
                "Количество постов: ",
                validator::isValidPostCount,
                String.format("Введите число от от %d до %d",
                        Validator.POST_MIN_COUNT,
                        Validator.POST_MAX_COUNT)
        );
    }

    private User readUserFromConsole() {

        String name = readName();
        String password = readPassword();
        String email = readEmail();
        int postCount = readPostCount();

        return new User.Builder()
                .name(name)
                .password(password)
                .email(email)
                .postCount(postCount)
                .build();
    }

    private User readUserWithIndex(int index) {
        System.out.printf("\n=== Ввод пользователя #%d ===\n", index);
        return readUserFromConsole();
    }

    @Override
    public List<User> provideUsers(Integer size) {
        Objects.requireNonNull(size, "Size must not be null");

        if (size < 0) {
            throw new IllegalArgumentException("Size must not be negative");
        }

        return IntStream.range(0, size)
                .mapToObj(i -> readUserWithIndex(i + 1))
                .toList();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
