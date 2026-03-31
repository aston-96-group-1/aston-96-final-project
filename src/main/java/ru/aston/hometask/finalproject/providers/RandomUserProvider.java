package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;

import java.util.Random;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class RandomUserProvider implements IUserProvider {
    public final static String DESCRIPTION = "Заполнение списка пользователей случайным образом.";

    private final Validator validator;
    private final Random random;

    private final static Map<Integer, String> USER_NAMES = Map.of(
            0, "cat",
            1, "greg",
            2, "door",
            3, "aunt",
            4, "chair"
    );

    private final static List<String> DOMAINS = List.of("gmail.com", "yandex.ru", "mail.ru");

    private final static Map<Integer, Map<String, Integer>> SYMBOLS_RANGE = Map.of(
            0, Map.of("start", 65, "end", 90),
            1, Map.of("start", 97, "end", 122),
            2, Map.of("start", 48, "end", 57)
    );

    public RandomUserProvider(final Validator validator, final Random random) {
        this.validator = validator;
        this.random = random;
    }

    private int getRandomInt(final int start, final int end) {
        return random.nextInt(end - start + 1) + start;
    }

    public String generatePassword() {
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

    public String generateEmail(final String name) {
        final StringBuilder sb = new StringBuilder();
        final int domainId = getRandomInt(0, DOMAINS.size() - 1);

        sb.append(name).append("@").append(DOMAINS.get(domainId));

        return sb.toString();
    }

    public String generateName(final Set<String> nameSet) {
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
        final User[] users = new User[size];
        final Set<String> names = new HashSet<>();

        return Arrays.stream(users).map(user -> {
            String name = generateName(names);
            String email = generateEmail(name);
            String password = generatePassword();

            names.add(name);

            if (validator.validate(name, password, email, 0)) {
                user = new User.Builder().name(name).email(email).password(password).build();
            }

            return user;
        }).toList();
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    public static void main(String[] args) {
        Validator validator1 = new Validator();
        Random random1 = new Random();

        RandomUserProvider randomUserProvider = new RandomUserProvider(validator1, random1);
        List<User> users = randomUserProvider.provideUsers(5);

        users.forEach(System.out::println);
    }
}
