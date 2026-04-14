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

class RandomUserProviderIntegrationTest {

    private RandomUserProvider provider;

    @BeforeEach
    void setUp() {
        provider = new RandomUserProvider(new Validator(), new Random());
    }

    @Test
    void provideUsers_shouldGenerateAllValidUsers() {
        int size = 10;

        List<User> users = provider.provideUsers(size);

        assertEquals(size, users.size());
        for (User user : users) {
            assertNotNull(user.getName());
            int passwordLength = user.getPassword().length();
            assertTrue(passwordLength >= Validator.PASSWORD_MIN_LENGTH);
            assertTrue(passwordLength <= Validator.PASSWORD_MAX_LENGTH);
            assertTrue(user.getEmail().contains("@"));
            assertTrue(user.getPostCount() >= Validator.POST_MIN_COUNT);
            assertTrue(user.getPostCount() <= Validator.POST_MAX_COUNT);
        }
    }
}