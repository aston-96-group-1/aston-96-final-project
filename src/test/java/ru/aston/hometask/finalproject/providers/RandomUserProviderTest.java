package ru.aston.hometask.finalproject.providers;

import ru.aston.hometask.finalproject.models.User;
import ru.aston.hometask.finalproject.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RandomUserProviderTest {

    private Validator validator;
    private Random random;
    private RandomUserProvider provider;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        random = new Random(42L);
        provider = new RandomUserProvider(validator, random);
    }

    @Test
    void provideUsers_shouldReturnListOfUsers_whenValidSize() {
        int size = 5;

        List<User> users = provider.provideUsers(size);

        assertNotNull(users);
        assertEquals(size, users.size());
        assertTrue(users.stream().allMatch(user -> user != null));
    }

    @Test
    void provideUsers_shouldGenerateAllRequiredFields() {
        int size = 1;

        List<User> users = provider.provideUsers(size);
        User user = users.get(0);

        assertNotNull(user.getName());
        assertFalse(user.getName().isEmpty());
        assertNotNull(user.getEmail());
        assertFalse(user.getEmail().isEmpty());
        assertNotNull(user.getPassword());
        assertFalse(user.getPassword().isEmpty());
        assertTrue(user.getPostCount() >= Validator.POST_MIN_COUNT);
        assertTrue(user.getPostCount() <= Validator.POST_MAX_COUNT);
    }

    @Test
    void provideUsers_shouldGenerateValidEmailFormat() {
        int size = 10;

        List<User> users = provider.provideUsers(size);

        for (User user : users) {
            assertTrue(user.getEmail().contains("@"));
            assertFalse(user.getEmail().contains(" "));
            String[] parts = user.getEmail().split("@");
            assertEquals(2, parts.length);
            assertFalse(parts[0].isEmpty());
            String domain = parts[1];
            assertTrue(domain.equals("gmail.com") ||
                    domain.equals("yandex.ru") ||
                    domain.equals("mail.ru") ||
                    domain.equals("example.com"));
        }
    }

    @Test
    void provideUsers_shouldGeneratePasswordWithValidLength() {
        int size = 20;

        List<User> users = provider.provideUsers(size);

        for (User user : users) {
            int length = user.getPassword().length();
            assertTrue(length >= Validator.PASSWORD_MIN_LENGTH);
            assertTrue(length <= Validator.PASSWORD_MAX_LENGTH);
        }
    }

    @Test
    void provideUsers_shouldGeneratePasswordWithValidCharacters() {
        int size = 50;

        List<User> users = provider.provideUsers(size);

        for (User user : users) {
            String password = user.getPassword();
            assertTrue(password.matches("^[A-Za-z0-9]+$"));
        }
    }

    @Test
    void provideUsers_shouldHandleNameUniquenessWithNumber() {
        int size = 20;

        List<User> users = provider.provideUsers(size);
        long uniqueNamesCount = users.stream().map(User::getName).distinct().count();

        assertEquals(size, uniqueNamesCount);

        for (User user : users) {
            String name = user.getName();
            if (name.matches(".*\\d+$")) {
                String baseName = name.replaceAll("\\d+$", "");
                assertTrue(baseName.equals("cat") ||
                        baseName.equals("greg") ||
                        baseName.equals("door") ||
                        baseName.equals("aunt") ||
                        baseName.equals("chair") ||
                        baseName.equals("raskol") ||
                        baseName.equals("harmony"));
            }
        }
    }

    @Test
    void provideUsers_shouldGenerateUniqueNames_whenDuplicatesOccur() {
        int size = 15;

        List<User> users = provider.provideUsers(size);
        List<String> names = users.stream().map(User::getName).toList();
        long uniqueCount = names.stream().distinct().count();

        assertEquals(names.size(), uniqueCount);
    }

    @Test
    void provideUsers_shouldGeneratePostCountWithinBounds() {
        int size = 10;

        List<User> users = provider.provideUsers(size);

        for (User user : users) {
            assertTrue(user.getPostCount() >= Validator.POST_MIN_COUNT);
            assertTrue(user.getPostCount() <= Validator.POST_MAX_COUNT);
        }
    }

    @Test
    void provideUsers_shouldFilterOutInvalidUsers() {
        int size = 5;

        List<User> users = provider.provideUsers(size);

        assertEquals(size, users.size());
        for (User user : users) {
            assertTrue(validator.validate(
                    user.getName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPostCount()
            ));
        }
    }

    @Test
    void provideUsers_shouldThrowNullPointerException_whenSizeIsNull() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            provider.provideUsers(null);
        });
        assertEquals("Must not be null!", exception.getMessage());
    }

    @Test
    void provideUsers_shouldThrowIllegalArgumentException_whenSizeIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            provider.provideUsers(-1);
        });
        assertEquals("Размер списка не может быть отрицательным!", exception.getMessage());
    }

    @Test
    void provideUsers_shouldHandleZeroSize() {
        int size = 0;

        List<User> users = provider.provideUsers(size);

        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    void getDescription_shouldReturnCorrectDescription() {
        String description = provider.getDescription();
        assertEquals("Заполнение списка пользователей случайным образом.", description);
    }

    @Test
    void provideUsers_shouldGenerateOnlyValidUsers_whenAllGeneratedValid() {
        int size = 7;

        List<User> users = provider.provideUsers(size);

        assertEquals(size, users.size());
        for (User user : users) {
            assertNotNull(user.getName());
            assertNotNull(user.getEmail());
            assertNotNull(user.getPassword());
            assertTrue(validator.validate(
                    user.getName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPostCount()
            ));
        }
    }

    @Test
    void provideUsers_shouldValidateEachGeneratedUser() {
        int size = 3;

        List<User> users = provider.provideUsers(size);

        assertEquals(size, users.size());
        for (User user : users) {
            assertTrue(validator.validate(
                    user.getName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getPostCount()
            ));
        }
    }
}