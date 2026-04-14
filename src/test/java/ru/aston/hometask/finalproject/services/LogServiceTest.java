package ru.aston.hometask.finalproject.services;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.hometask.finalproject.filesystem.FileWriter;
import ru.aston.hometask.finalproject.models.User;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
public class LogServiceTest {
    @Mock
    private Dotenv dotenv;

    @Mock
    private FileWriter fileWriter;

    private LogService logService;

    @BeforeEach
    void setup() {
        logService = new LogService(dotenv, fileWriter);
    }

    @Test
    void logSorted_ShouldCallFileWriterWithCorrectPath() {
        String fakePath = "test/output.txt";
        List<User> users = createTestUsers();
        when(dotenv.get("OUTPUT")).thenReturn(fakePath);

        String result = logService.logSorted(users);

        verify(fileWriter).writeToFile(fakePath, users);

        assertTrue(result.contains(fakePath));
    }

    @Test
    void logFound_ShouldCallFileWriterTwice() {
        String fakePath = "test/found.txt";
        User user = createTestUsers().getFirst();
        int count = 5;
        when(dotenv.get("OUTPUT")).thenReturn(fakePath);

        logService.logFound(user, count);

        verify(fileWriter).writeToFile(eq(fakePath), eq(List.of(user)));
        verify(fileWriter).writeToFile(eq(fakePath), eq(List.of(count)));

        verify(fileWriter, times(2)).writeToFile(eq(fakePath), any());
    }

    private List<User> createTestUsers() {
        return Arrays.asList(
                createUser("John Doe", "password123", "john@example.com", 10),
                createUser("Jane Smith", "password456", "jane@example.com", 15)
        );
    }

    private User createUser(String name, String password, String email, int postCount) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .postCount(postCount)
                .build();
    }
}
