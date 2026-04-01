package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RandomUserProvider implements IUserProvider {
    public static final String DESCRIPTION = "Заполнение списка пользователей случайным образом.";

    private static final List<String> USER_NAMES = List.of("cat", "greg", "door", "aunt", "chair", "raskol", "harmony");
    private static final List<String> DOMAINS = List.of("gmail.com", "yandex.ru", "mail.ru", "example.com");
    private static final List<Map<String, Integer>> SYMBOLS_RANGE = List.of(
            Map.of("start", 65, "end", 90),
            Map.of("start", 97, "end", 122),
            Map.of("start", 48, "end", 57)
    );

    private final Validator validator;
    private final Random random;

    public RandomUserProvider(final Validator validator, final Random random) {
        this.validator = validator;
        this.random = random;
    }

    private int getRandomInt(final int start, final int end) {
        return random.nextInt(end - start + 1) + start;
    }

    private String generatePassword() {
        final int passwordLength = getRandomInt(Validator.PASSWORD_MIN_LENGTH, Validator.PASSWORD_MAX_LENGTH);
        final StringBuilder sb = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            final int rangeId = getRandomInt(0, SYMBOLS_RANGE.size() - 1);
            final Map<String, Integer> range = SYMBOLS_RANGE.get(rangeId);
            final char ch = (char) getRandomInt(range.get("start"), range.get("end"));
            sb.append(ch);
        }

        return sb.toString();
    }

    private String concatEmail(final String name) {
        final StringBuilder sb = new StringBuilder();
        final int domainId = getRandomInt(0, DOMAINS.size() - 1);

        sb.append(name).append("@").append(DOMAINS.get(domainId));

        return sb.toString();
    }

    private String generateName(final Set<String> nameSet) {
        final String baseName = USER_NAMES.get(getRandomInt(0, USER_NAMES.size() - 1));
        final StringBuilder sb = new StringBuilder(baseName);

        final int baseLength = baseName.length();

        String finalName = baseName;

        int count = 1;
        while (nameSet.contains(finalName)) {
            sb.setLength(baseLength);
            sb.append(count);
            finalName = sb.toString();
            count++;
        }

        return finalName;
    }

    @Override
    public List<User> provideUsers(Integer size) {
        if (size <= 0) {
            return Collections.emptyList();
        }

        final User[] users = new User[size];
        final Set<String> names = new HashSet<>();

        return Arrays.stream(users)
                .map(user -> {
                    final String name = generateName(names);
                    final String email = concatEmail(name);
                    final String password = generatePassword();
                    final int postCount = getRandomInt(Validator.POST_MIN_COUNT, Validator.POST_MAX_COUNT);

                    names.add(name);

                    if (validator.validate(name, password, email, postCount)) {
                        user = new User.Builder().name(name).email(email).password(password).postCount(postCount).build();
                    }

                    return user;
                }).toList();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
