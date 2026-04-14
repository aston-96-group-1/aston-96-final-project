package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.constants.Strings;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ManualUserProvider implements IUserProvider {
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
                Strings.NAME_PROMPT.get(),
                validator::isValidName,
                Strings.ERROR_NAME_FORMAT.get()
        );
    }

    private String readPassword() {
        return readValidated(
                Strings.PASSWORD_PROMPT.get(),
                validator::isValidPassword,
                String.format(Strings.ERROR_PASSWORD_FORMAT.get(),
                        Validator.PASSWORD_MIN_LENGTH,
                        Validator.PASSWORD_MAX_LENGTH)
        );
    }

    private String readEmail() {
        return readValidated(
                Strings.EMAIL_PROMPT.get(),
                validator::isValidEmail,
                Strings.ERROR_EMAIL_FORMAT.get()
        );
    }

    private int readPostCount() {
        return readValidatedInt(
                Strings.POST_COUNT_PROMPT.get(),
                validator::isValidPostCount,
                String.format(Strings.ERROR_POST_COUNT_FORMAT.get(),
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
        System.out.printf(Strings.USER_INPUT_TITLE.get(), index);
        return readUserFromConsole();
    }

    @Override
    public List<User> provideUsers(Integer size) {
        Objects.requireNonNull(size, Strings.ERROR_OBJECT_IS_NULL.get());

        if (size < 0) {
            throw new IllegalArgumentException(Strings.ERROR_SIZE_IS_NEGATIVE.get());
        }

        return IntStream.range(0, size)
                .mapToObj(i -> readUserWithIndex(i + 1))
                .collect(Collectors.toList());
    }

    @Override
    public String getDescription() {
        return Strings.MANUAL_USER_PROVIDER_TITLE.get();
    }
}
