package ru.aston.hometask.finalproject.services;

import org.junit.jupiter.api.Test;
import ru.aston.hometask.finalproject.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class UserCounterConcurrencyTest {

    private User createUser(String name, String password, String email, int postCount) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .postCount(postCount)
                .build();
    }

    @Test
    void countUsers_shouldBeThreadSafe() throws InterruptedException {
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        List<User> sharedList = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                sharedList.add(targetUser);
            } else {
                sharedList.add(createUser("other" + i, "pass" + i, "other@test.com", i));
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> {
                try {
                    UserCounter.countUsers(sharedList, targetUser);
                } finally {
                    latch.countDown();
                }
            });
        }

        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executor.shutdown();
        assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
    }

    @Test
    void countUsers_shouldHandleConcurrentReads() {
        User targetUser = createUser("john", "pass123", "john@test.com", 10);
        List<User> users = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            users.add(targetUser);
        }

        for (int i = 0; i < 10; i++) {
            int result = UserCounter.countUsers(users, targetUser);
            assertEquals(100, result);
        }
    }
}