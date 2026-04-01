package ru.aston.hometask.finalproject.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validator Tests")
class ValidatorTest {

    private final Validator validator = new Validator();

    @Test
    @DisplayName("Should return true for valid names")
    void isValidName_ShouldReturnTrue_ForValidNames() {
        assertTrue(validator.isValidName("John"));
        assertTrue(validator.isValidName("john123"));
        assertTrue(validator.isValidName("123"));
        assertTrue(validator.isValidName("A"));
        assertTrue(validator.isValidName("Z"));
        assertTrue(validator.isValidName("0"));
        assertTrue(validator.isValidName("a1b2c3"));
        assertTrue(validator.isValidName("User123"));
    }

    @Test
    @DisplayName("Should return false for invalid names")
    void isValidName_ShouldReturnFalse_ForInvalidNames() {
        assertFalse(validator.isValidName(null));
        assertFalse(validator.isValidName(""));
        assertFalse(validator.isValidName("John Doe"));
        assertFalse(validator.isValidName("John@"));
        assertFalse(validator.isValidName("user-name"));
        assertFalse(validator.isValidName("user.name"));
        assertFalse(validator.isValidName("Привет"));
        assertFalse(validator.isValidName("  "));
        assertFalse(validator.isValidName("\t"));
        assertFalse(validator.isValidName("\n"));
    }

    @Test
    @DisplayName("Should return true for valid passwords")
    void isValidPassword_ShouldReturnTrue_ForValidPasswords() {
        assertTrue(validator.isValidPassword("abcdefgh"));
        assertTrue(validator.isValidPassword("ABCDEFGH"));
        assertTrue(validator.isValidPassword("12345678"));
        assertTrue(validator.isValidPassword("aBcDeFgH"));
        assertTrue(validator.isValidPassword("Abc12345"));
        assertTrue(validator.isValidPassword("password123"));
        assertTrue(validator.isValidPassword("PASSWORD"));
        assertTrue(validator.isValidPassword("a1b2c3d4"));
        assertTrue(validator.isValidPassword("12345678901234567890"));
    }

    @Test
    @DisplayName("Should return false for invalid passwords")
    void isValidPassword_ShouldReturnFalse_ForInvalidPasswords() {
        assertFalse(validator.isValidPassword(null));
        assertFalse(validator.isValidPassword(""));
        assertFalse(validator.isValidPassword("abc"));
        assertFalse(validator.isValidPassword("abcdefg"));
        assertFalse(validator.isValidPassword("123456789012345678901"));
        assertFalse(validator.isValidPassword("password!"));
        assertFalse(validator.isValidPassword("pass word"));
        assertFalse(validator.isValidPassword("pass@word"));
        assertFalse(validator.isValidPassword("pass-word"));
        assertFalse(validator.isValidPassword("pass.word"));
        assertFalse(validator.isValidPassword("pass word123"));
        assertFalse(validator.isValidPassword("Привет123"));
        assertFalse(validator.isValidPassword("\tpassword"));
        assertFalse(validator.isValidPassword("pass\nword"));
        assertFalse(validator.isValidPassword("pass\rword"));
    }

    @Test
    @DisplayName("Should return false for password with exactly minimum length but invalid characters")
    void isValidPassword_ShouldReturnFalse_WhenLengthOkButInvalidChars() {
        String password = "pass!@#$";
        assertFalse(validator.isValidPassword(password));
    }

    @Test
    @DisplayName("Should return false for password with exactly maximum length but invalid characters")
    void isValidPassword_ShouldReturnFalse_WhenMaxLengthButInvalidChars() {
        String password = "1234567890123456789!";
        assertFalse(validator.isValidPassword(password));
    }

    @Test
    @DisplayName("Should return true for valid emails")
    void isValidEmail_ShouldReturnTrue_ForValidEmails() {
        assertTrue(validator.isValidEmail("user@example.com"));
        assertTrue(validator.isValidEmail("user.name@example.com"));
        assertTrue(validator.isValidEmail("user-name@example.com"));
        assertTrue(validator.isValidEmail("user+name@example.com"));
        assertTrue(validator.isValidEmail("user_name@example.com"));
        assertTrue(validator.isValidEmail("user123@example.com"));
        assertTrue(validator.isValidEmail("user@sub.example.com"));
        assertTrue(validator.isValidEmail("user@example.co.uk"));
        assertTrue(validator.isValidEmail("test@test.com"));
    }

    @Test
    @DisplayName("Should return false for invalid emails")
    void isValidEmail_ShouldReturnFalse_ForInvalidEmails() {
        assertFalse(validator.isValidEmail(null));
        assertFalse(validator.isValidEmail(""));
        assertFalse(validator.isValidEmail("user@"));
        assertFalse(validator.isValidEmail("@example.com"));
        assertFalse(validator.isValidEmail("user@example"));
        assertFalse(validator.isValidEmail("user@.com"));
        assertFalse(validator.isValidEmail("user.example.com"));
        assertFalse(validator.isValidEmail("user name@example.com"));
        assertFalse(validator.isValidEmail("user@example .com"));
        assertFalse(validator.isValidEmail("user@exam ple.com"));
    }

    @Test
    @DisplayName("Should return true for valid post counts")
    void isValidPostCount_ShouldReturnTrue_ForValidPostCounts() {
        assertTrue(validator.isValidPostCount(0));
        assertTrue(validator.isValidPostCount(1));
        assertTrue(validator.isValidPostCount(100));
        assertTrue(validator.isValidPostCount(255));
    }

    @Test
    @DisplayName("Should return false for invalid post counts")
    void isValidPostCount_ShouldReturnFalse_ForInvalidPostCounts() {
        assertFalse(validator.isValidPostCount(-1));
        assertFalse(validator.isValidPostCount(-100));
        assertFalse(validator.isValidPostCount(256));
        assertFalse(validator.isValidPostCount(1000));
    }

    @Test
    @DisplayName("Should return true for post count exactly at minimum boundary")
    void isValidPostCount_ShouldReturnTrue_AtMinBoundary() {
        assertTrue(validator.isValidPostCount(Validator.POST_MIN_COUNT));
    }

    @Test
    @DisplayName("Should return true for post count exactly at maximum boundary")
    void isValidPostCount_ShouldReturnTrue_AtMaxBoundary() {
        assertTrue(validator.isValidPostCount(Validator.POST_MAX_COUNT));
    }

    @Test
    @DisplayName("Should return false for post count just below minimum")
    void isValidPostCount_ShouldReturnFalse_BelowMin() {
        assertFalse(validator.isValidPostCount(Validator.POST_MIN_COUNT - 1));
    }

    @Test
    @DisplayName("Should return false for post count just above maximum")
    void isValidPostCount_ShouldReturnFalse_AboveMax() {
        assertFalse(validator.isValidPostCount(Validator.POST_MAX_COUNT + 1));
    }

    @Test
    @DisplayName("Should return true when all fields are valid")
    void validate_ShouldReturnTrue_WhenAllFieldsValid() {
        assertTrue(validator.validate("JohnDoe", "password123", "john@example.com", 10));
    }

    @Test
    @DisplayName("Should return false when name is invalid")
    void validate_ShouldReturnFalse_WhenNameInvalid() {
        assertFalse(validator.validate("John Doe", "password123", "john@example.com", 10));
    }

    @Test
    @DisplayName("Should return false when password is invalid")
    void validate_ShouldReturnFalse_WhenPasswordInvalid() {
        assertFalse(validator.validate("JohnDoe", "pass", "john@example.com", 10));
    }

    @Test
    @DisplayName("Should return false when email is invalid")
    void validate_ShouldReturnFalse_WhenEmailInvalid() {
        assertFalse(validator.validate("JohnDoe", "password123", "invalid-email", 10));
    }

    @Test
    @DisplayName("Should return false when post count is invalid")
    void validate_ShouldReturnFalse_WhenPostCountInvalid() {
        assertFalse(validator.validate("JohnDoe", "password123", "john@example.com", 256));
    }

    @Test
    @DisplayName("Should return false when multiple fields are invalid")
    void validate_ShouldReturnFalse_WhenMultipleFieldsInvalid() {
        assertFalse(validator.validate("John Doe", "pass", "invalid-email", 256));
    }

    @Test
    @DisplayName("Should return false when name is null")
    void validate_ShouldReturnFalse_WhenNameIsNull() {
        assertFalse(validator.validate(null, "password123", "john@example.com", 10));
    }

    @Test
    @DisplayName("Should return false when password is null")
    void validate_ShouldReturnFalse_WhenPasswordIsNull() {
        assertFalse(validator.validate("JohnDoe", null, "john@example.com", 10));
    }

    @Test
    @DisplayName("Should return false when email is null")
    void validate_ShouldReturnFalse_WhenEmailIsNull() {
        assertFalse(validator.validate("JohnDoe", "password123", null, 10));
    }

    @Test
    @DisplayName("Should return false when all fields are null or invalid")
    void validate_ShouldReturnFalse_WhenAllFieldsInvalid() {
        assertFalse(validator.validate(null, null, null, -1));
    }

    @Test
    @DisplayName("Should have correct constant values")
    void constants_ShouldHaveCorrectValues() {
        assertEquals(8, Validator.PASSWORD_MIN_LENGTH);
        assertEquals(20, Validator.PASSWORD_MAX_LENGTH);
        assertEquals("^[a-zA-Z0-9]+$", Validator.NAME_PATTERN);
        assertEquals("^[a-zA-Z0-9]+$", Validator.PASSWORD_PATTERN);
        assertEquals("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", Validator.EMAIL_PATTERN);
        assertEquals(0, Validator.POST_MIN_COUNT);
        assertEquals(255, Validator.POST_MAX_COUNT);
    }
}