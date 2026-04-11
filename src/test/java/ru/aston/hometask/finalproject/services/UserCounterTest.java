package ru.aston.hometask.finalproject.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.aston.hometask.finalproject.models.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserCounterTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    private User createUser(String name, String password, String email, int postCount) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .postCount(postCount)
                .build();
    }

    @Test
    void countUsers_shouldReturnZero_whenListIsEmpty() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
        String output = outContent.toString();
        assertTrue(output.isEmpty());
    }

    @Test
    void countUsers_shouldReturnCorrectCount_whenTargetUserExistsOnce() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User user1 = createUser("john", "pass123", "john@test.com", 10);
        User user2 = createUser("jane", "pass456", "jane@test.com", 5);
        User user3 = createUser("bob", "pass789", "bob@test.com", 3);

        users.add(user1);
        users.add(user2);
        users.add(user3);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(1, result);
        String output = outContent.toString();
        assertTrue(output.contains("Количество вхождений элемента User{"));
        assertTrue(output.contains("в коллекцию: 1"));
    }

    @Test
    void countUsers_shouldReturnCorrectCount_whenTargetUserExistsMultipleTimes() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User sameUser1 = createUser("john", "pass123", "john@test.com", 10);
        User differentUser = createUser("jane", "pass456", "jane@test.com", 5);
        User sameUser2 = createUser("john", "pass123", "john@test.com", 10);
        User sameUser3 = createUser("john", "pass123", "john@test.com", 10);

        users.add(sameUser1);
        users.add(differentUser);
        users.add(sameUser2);
        users.add(sameUser3);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(3, result);
        String output = outContent.toString();
        assertTrue(output.contains("Количество вхождений элемента User{"));
        assertTrue(output.contains("в коллекцию: 3"));
    }

    @Test
    void countUsers_shouldReturnZero_whenTargetUserDoesNotExist() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User user1 = createUser("jane", "pass456", "jane@test.com", 5);
        User user2 = createUser("bob", "pass789", "bob@test.com", 3);

        users.add(user1);
        users.add(user2);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
        String output = outContent.toString();
        assertTrue(output.contains("Количество вхождений элемента User{"));
        assertTrue(output.contains("в коллекцию: 0"));
    }

    @Test
    void countUsers_shouldWorkWithSingleElementList_whenTargetMatches() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User sameUser = createUser("john", "pass123", "john@test.com", 10);

        users.add(sameUser);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(1, result);
        String output = outContent.toString();
        assertTrue(output.contains("в коллекцию: 1"));
    }

    @Test
    void countUsers_shouldWorkWithSingleElementList_whenTargetDoesNotMatch() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User differentUser = createUser("jane", "pass456", "jane@test.com", 5);

        users.add(differentUser);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
        String output = outContent.toString();
        assertTrue(output.contains("в коллекцию: 0"));
    }

    @Test
    void countUsers_shouldWorkWithLargeList() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        for (int i = 0; i < 100; i++) {
            if (i % 4 == 0) {
                users.add(createUser("john", "pass123", "john@test.com", 10));
            } else {
                users.add(createUser("user" + i, "pass" + i, "user" + i + "@test.com", i));
            }
        }

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(25, result);
        String output = outContent.toString();
        assertTrue(output.contains("в коллекцию: 25"));
    }

    @Test
    void countUsers_shouldUseMultipleThreads() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        for (int i = 0; i < 50; i++) {
            if (i < 30) {
                users.add(createUser("john", "pass123", "john@test.com", 10));
            } else {
                users.add(createUser("other" + i, "pass" + i, "other" + i + "@test.com", i));
            }
        }

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(30, result);
    }

    @Test
    void countUsers_shouldHandleListSizeOne() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        users.add(targetUser);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(1, result);
    }

    @Test
    void countUsers_shouldHandleListSizeTwo() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        users.add(targetUser);
        users.add(createUser("jane", "pass456", "jane@test.com", 5));

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(1, result);
    }

    @Test
    void countUsers_shouldHandleListSizeThree() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        users.add(targetUser);
        users.add(createUser("jane", "pass456", "jane@test.com", 5));
        users.add(targetUser);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(2, result);
    }

    @Test
    void countUsers_shouldPrintCorrectFormat() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        users.add(targetUser);

        UserCounter.countUsers(users, targetUser);

        String output = outContent.toString();
        assertTrue(output.contains("Количество вхождений элемента User{name: john; password: pass123; email: john@test.com; postCount: 10}  в коллекцию: 1"));
    }

    @Test
    void countUsers_shouldHandleDifferentObjectsWithSameFields() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User sameFieldsUser1 = createUser("john", "pass123", "john@test.com", 10);
        User sameFieldsUser2 = createUser("john", "pass123", "john@test.com", 10);

        users.add(sameFieldsUser1);
        users.add(sameFieldsUser2);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(2, result);
    }

    @Test
    void countUsers_shouldNotCountSimilarButDifferentUsers() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User similarName = createUser("johnny", "pass123", "john@test.com", 10);
        User similarPassword = createUser("john", "pass1234", "john@test.com", 10);
        User similarEmail = createUser("john", "pass123", "johnny@test.com", 10);
        User similarPostCount = createUser("john", "pass123", "john@test.com", 5);

        users.add(similarName);
        users.add(similarPassword);
        users.add(similarEmail);
        users.add(similarPostCount);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
    }

    @Test
    void countUsers_shouldHandleWhenSizeIsExactlyDivisibleByThreads() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        for (int i = 0; i < 100; i++) {
            users.add(targetUser);
        }

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(100, result);
    }

    @Test
    void countUsers_shouldHandleWhenSizeIsNotExactlyDivisibleByThreads() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        for (int i = 0; i < 101; i++) {
            users.add(targetUser);
        }

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(101, result);
    }

    @Test
    void countUsers_shouldHandleMinimumListSize() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        users.add(targetUser);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(1, result);
    }

    @Test
    void countUsers_shouldHandleListSizeTwoWithNoMatches() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        users.add(createUser("jane", "pass456", "jane@test.com", 5));
        users.add(createUser("bob", "pass789", "bob@test.com", 3));

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
    }

    @Test
    void countUsers_shouldHandleNullValuesInList() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        User sameUser = createUser("john", "pass123", "john@test.com", 10);

        users.add(null);
        users.add(sameUser);
        users.add(null);
        users.add(targetUser);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(2, result);
        String output = outContent.toString();
        assertTrue(output.contains("в коллекцию: 2"));
    }

    @Test
    void countUsers_shouldHandleListWithAllNulls() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        users.add(null);
        users.add(null);
        users.add(null);

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
        String output = outContent.toString();
        assertTrue(output.contains("в коллекцию: 0"));
    }

    @Test
    void countUsers_shouldHandleMixedNullAndNonMatchingUsers() {
        List<User> users = new ArrayList<>();
        User targetUser = createUser("john", "pass123", "john@test.com", 10);

        users.add(null);
        users.add(createUser("jane", "pass456", "jane@test.com", 5));
        users.add(null);
        users.add(createUser("bob", "pass789", "bob@test.com", 3));

        int result = UserCounter.countUsers(users, targetUser);

        assertEquals(0, result);
        String output = outContent.toString();
        assertTrue(output.contains("в коллекцию: 0"));
    }
}