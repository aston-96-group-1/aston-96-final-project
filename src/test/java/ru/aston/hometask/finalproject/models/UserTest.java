package ru.aston.hometask.finalproject.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class UserTest {

    @Nested
    @DisplayName("Builder Pattern Tests")
    class BuilderTests {

        @Test
        @DisplayName("Should build User with all fields set")
        void shouldBuildUserWithAllFields() {
            User user = User.builder()
                    .name("John Doe")
                    .password("secret123")
                    .email("john@example.com")
                    .postCount(15)
                    .build();

            assertAll("User properties",
                    () -> assertEquals("John Doe", user.getName()),
                    () -> assertEquals("secret123", user.getPassword()),
                    () -> assertEquals("john@example.com", user.getEmail()),
                    () -> assertEquals(15, user.getPostCount())
            );
        }

        @Test
        @DisplayName("Should build User with only required fields")
        void shouldBuildUserWithRequiredFields() {
            User user = User.builder()
                    .name("Jane Doe")
                    .password("pass123")
                    .email("jane@example.com")
                    .build();

            assertAll("User properties",
                    () -> assertEquals("Jane Doe", user.getName()),
                    () -> assertEquals("pass123", user.getPassword()),
                    () -> assertEquals("jane@example.com", user.getEmail()),
                    () -> assertEquals(0, user.getPostCount())
            );
        }

        @Test
        @DisplayName("Should build User with zero post count")
        void shouldBuildUserWithZeroPostCount() {
            User user = User.builder()
                    .name("Test User")
                    .password("test123")
                    .email("test@example.com")
                    .postCount(0)
                    .build();

            assertEquals(0, user.getPostCount());
        }

        @Test
        @DisplayName("Should build User with negative post count")
        void shouldBuildUserWithNegativePostCount() {
            User user = User.builder()
                    .name("Negative User")
                    .password("pass")
                    .email("negative@example.com")
                    .postCount(-5)
                    .build();

            assertEquals(-5, user.getPostCount());
        }

        @Test
        @DisplayName("Should support method chaining in builder")
        void shouldSupportMethodChaining() {
            User.Builder builder = User.builder();

            assertSame(builder, builder.name("Name"));
            assertSame(builder, builder.password("Pass"));
            assertSame(builder, builder.email("email@test.com"));
            assertSame(builder, builder.postCount(10));
        }
    }

    @Nested
    @DisplayName("Getters Tests")
    class GettersTests {

        @Test
        @DisplayName("Should return correct name")
        void shouldReturnCorrectName() {
            User user = User.builder()
                    .name("Test Name")
                    .password("pass")
                    .email("test@test.com")
                    .build();

            assertEquals("Test Name", user.getName());
        }

        @Test
        @DisplayName("Should return correct password")
        void shouldReturnCorrectPassword() {
            User user = User.builder()
                    .name("User")
                    .password("securePass123")
                    .email("user@test.com")
                    .build();

            assertEquals("securePass123", user.getPassword());
        }

        @Test
        @DisplayName("Should return correct email")
        void shouldReturnCorrectEmail() {
            User user = User.builder()
                    .name("Email Test")
                    .password("pass")
                    .email("unique@email.com")
                    .build();

            assertEquals("unique@email.com", user.getEmail());
        }

        @Test
        @DisplayName("Should return correct post count")
        void shouldReturnCorrectPostCount() {
            User user = User.builder()
                    .name("Post Counter")
                    .password("pass")
                    .email("post@test.com")
                    .postCount(42)
                    .build();

            assertEquals(42, user.getPostCount());
        }
    }

    @Nested
    @DisplayName("Equals and HashCode Tests")
    class EqualsAndHashCodeTests {

        @Test
        @DisplayName("Should return true when comparing equal users")
        void shouldReturnTrueForEqualUsers() {
            User user1 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            User user2 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            assertEquals(user1, user2);
            assertEquals(user1.hashCode(), user2.hashCode());
        }

        @Test
        @DisplayName("Should return false when comparing with null")
        void shouldReturnFalseWhenComparingWithNull() {
            User user = User.builder()
                    .name("John")
                    .password("pass")
                    .email("john@test.com")
                    .build();

            assertNotEquals(null, user);
        }

        @Test
        @DisplayName("Should return false when comparing with different class")
        void shouldReturnFalseWhenComparingWithDifferentClass() {
            User user = User.builder()
                    .name("John")
                    .password("pass")
                    .email("john@test.com")
                    .build();

            assertNotEquals(user, "not a user");
        }

        @Test
        @DisplayName("Should return false when names differ")
        void shouldReturnFalseWhenNamesDiffer() {
            User user1 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            User user2 = User.builder()
                    .name("Jane")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            assertNotEquals(user1, user2);
        }

        @Test
        @DisplayName("Should return false when passwords differ")
        void shouldReturnFalseWhenPasswordsDiffer() {
            User user1 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            User user2 = User.builder()
                    .name("John")
                    .password("different")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            assertNotEquals(user1, user2);
        }

        @Test
        @DisplayName("Should return false when emails differ")
        void shouldReturnFalseWhenEmailsDiffer() {
            User user1 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            User user2 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john2@test.com")
                    .postCount(10)
                    .build();

            assertNotEquals(user1, user2);
        }

        @Test
        @DisplayName("Should return false when post counts differ")
        void shouldReturnFalseWhenPostCountsDiffer() {
            User user1 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            User user2 = User.builder()
                    .name("John")
                    .password("pass123")
                    .email("john@test.com")
                    .postCount(20)
                    .build();

            assertNotEquals(user1, user2);
        }

        @Test
        @DisplayName("Should handle null values in equals")
        void shouldHandleNullValuesInEquals() {
            User user1 = User.builder()
                    .name(null)
                    .password(null)
                    .email(null)
                    .postCount(0)
                    .build();

            User user2 = User.builder()
                    .name(null)
                    .password(null)
                    .email(null)
                    .postCount(0)
                    .build();

            assertEquals(user1, user2);
        }

        @Test
        @DisplayName("Should return false when one field is null and other is not")
        void shouldReturnFalseWhenOneFieldIsNull() {
            User user1 = User.builder()
                    .name("John")
                    .password("pass")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            User user2 = User.builder()
                    .name(null)
                    .password("pass")
                    .email("john@test.com")
                    .postCount(10)
                    .build();

            assertNotEquals(user1, user2);
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {

        @Test
        @DisplayName("Should return correctly formatted string")
        void shouldReturnCorrectlyFormattedString() {
            User user = User.builder()
                    .name("Test User")
                    .password("secret")
                    .email("test@example.com")
                    .postCount(25)
                    .build();

            String expected = "name: Test User; password: secret; email: test@example.com; postCount: 25";
            assertEquals(expected, user.toString());
        }

        @Test
        @DisplayName("Should handle null values in toString")
        void shouldHandleNullValuesInToString() {
            User user = User.builder()
                    .name(null)
                    .password(null)
                    .email(null)
                    .postCount(0)
                    .build();

            String expected = "name: null; password: null; email: null; postCount: 0";
            assertEquals(expected, user.toString());
        }

        @Test
        @DisplayName("Should handle negative post count in toString")
        void shouldHandleNegativePostCountInToString() {
            User user = User.builder()
                    .name("Negative")
                    .password("pass")
                    .email("neg@test.com")
                    .postCount(-10)
                    .build();

            String expected = "name: Negative; password: pass; email: neg@test.com; postCount: -10";
            assertEquals(expected, user.toString());
        }

        @ParameterizedTest
        @DisplayName("Should handle different post count values in toString")
        @ValueSource(ints = {0, 1, 100, 999, -1, -100})
        void shouldHandleDifferentPostCountsInToString(int postCount) {
            User user = User.builder()
                    .name("User")
                    .password("pass")
                    .email("user@test.com")
                    .postCount(postCount)
                    .build();

            String expected = String.format("name: User; password: pass; email: user@test.com; postCount: %d", postCount);
            assertEquals(expected, user.toString());
        }
    }

    @Nested
    @DisplayName("Builder Static Method Tests")
    class BuilderStaticMethodTests {

        @Test
        @DisplayName("Should create new builder instance")
        void shouldCreateNewBuilderInstance() {
            User.Builder builder1 = User.builder();
            User.Builder builder2 = User.builder();

            assertNotNull(builder1);
            assertNotNull(builder2);
            assertNotSame(builder1, builder2);
        }

        @Test
        @DisplayName("Should build multiple users from separate builders")
        void shouldBuildMultipleUsersFromSeparateBuilders() {
            User user1 = User.builder()
                    .name("User1")
                    .password("pass1")
                    .email("user1@test.com")
                    .build();

            User user2 = User.builder()
                    .name("User2")
                    .password("pass2")
                    .email("user2@test.com")
                    .build();

            assertNotSame(user1, user2);
            assertEquals("User1", user1.getName());
            assertEquals("User2", user2.getName());
        }
    }

    @Nested
    @DisplayName("Edge Cases Tests")
    class EdgeCasesTests {

        @Test
        @DisplayName("Should handle empty strings")
        void shouldHandleEmptyStrings() {
            User user = User.builder()
                    .name("")
                    .password("")
                    .email("")
                    .postCount(0)
                    .build();

            assertEquals("", user.getName());
            assertEquals("", user.getPassword());
            assertEquals("", user.getEmail());
            assertEquals(0, user.getPostCount());
        }

        @Test
        @DisplayName("Should handle very long strings")
        void shouldHandleVeryLongStrings() {
            String longName = "A".repeat(1000);
            String longPassword = "B".repeat(1000);
            String longEmail = "C".repeat(500) + "@test.com";

            User user = User.builder()
                    .name(longName)
                    .password(longPassword)
                    .email(longEmail)
                    .postCount(Integer.MAX_VALUE)
                    .build();

            assertEquals(longName, user.getName());
            assertEquals(longPassword, user.getPassword());
            assertEquals(longEmail, user.getEmail());
            assertEquals(Integer.MAX_VALUE, user.getPostCount());
        }

        @Test
        @DisplayName("Should handle maximum integer post count")
        void shouldHandleMaxIntegerPostCount() {
            User user = User.builder()
                    .name("Max")
                    .password("pass")
                    .email("max@test.com")
                    .postCount(Integer.MAX_VALUE)
                    .build();

            assertEquals(Integer.MAX_VALUE, user.getPostCount());
        }

        @Test
        @DisplayName("Should handle minimum integer post count")
        void shouldHandleMinIntegerPostCount() {
            User user = User.builder()
                    .name("Min")
                    .password("pass")
                    .email("min@test.com")
                    .postCount(Integer.MIN_VALUE)
                    .build();

            assertEquals(Integer.MIN_VALUE, user.getPostCount());
        }

        @ParameterizedTest
        @DisplayName("Should handle various string inputs")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t", "\n", "  spaces  ", "special!@#$%^&*()"})
        void shouldHandleVariousStringInputs(String input) {
            User user = User.builder()
                    .name(input)
                    .password(input)
                    .email(input)
                    .build();

            assertEquals(input, user.getName());
            assertEquals(input, user.getPassword());
            assertEquals(input, user.getEmail());
        }
    }
}