package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.constants.RangeKey;
import ru.aston.hometask.finalproject.constants.SampleUserData;
import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomUserProvider implements IUserProvider {
    public static final String DESCRIPTION = "Заполнение списка пользователей случайным образом.";

    private static final List<String> USER_NAMES = SampleUserData.USER_NAMES.getList();

    private static final List<String> DOMAINS = SampleUserData.EMAIL_DOMAINS.getList();

    private static final Map<RangeKey, Integer> UPPER_CASE_LETTERS_RANGE = Map.of(RangeKey.START, 65, RangeKey.END, 90);
    private static final Map<RangeKey, Integer> LOWER_CASE_LETTERS_RANGE = Map.of(RangeKey.START, 97, RangeKey.END, 122);
    private static final Map<RangeKey, Integer> DIGITS_RANGE = Map.of(RangeKey.START, 48, RangeKey.END, 57);
    private static final List<Map<RangeKey, Integer>> SYMBOLS_RANGE = List.of(
            LOWER_CASE_LETTERS_RANGE,
            UPPER_CASE_LETTERS_RANGE,
            DIGITS_RANGE
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
            final Map<RangeKey, Integer> range = SYMBOLS_RANGE.get(rangeId);
            final char ch = (char) getRandomInt(range.get(RangeKey.START), range.get(RangeKey.END));
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
    public List<User> provideUsers(final Integer size) {
        if (size <= 0) {
            return Collections.emptyList();
        }

        final Set<String> names = new HashSet<>();

        return Stream.generate(() -> {
                    final String name = generateName(names);
                    final String email = concatEmail(name);
                    final String password = generatePassword();
                    final int postCount = getRandomInt(Validator.POST_MIN_COUNT, Validator.POST_MAX_COUNT);

                    names.add(name);

                    if (validator.validate(name, password, email, postCount)) {
                        return User.builder().name(name).email(email).password(password).postCount(postCount).build();
                    }

                    return null;
                })
                .limit(size)
                .collect(Collectors.toList());
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
