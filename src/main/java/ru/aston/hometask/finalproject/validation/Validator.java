package ru.aston.hometask.finalproject.validation;

public class Validator {
    public final static int PASSWORD_MIN_LENGTH = 8;
    public final static int PASSWORD_MAX_LENGTH = 20;
    public final static String NAME_PATTERN = "^[a-zA-Z0-9]+$";
    public final static String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";
    public static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,}$";
    public static final int POST_MIN_COUNT = 0;
    public static final int POST_MAX_COUNT = 255;

    public boolean isValidName(final String name) {
        if (name == null) {
            return false;
        }

        return name.matches(NAME_PATTERN);
    }

    public boolean isValidPassword(final String password) {
        if (password == null) {
            return false;
        }

        return password.matches(PASSWORD_PATTERN) && password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
    }

    public boolean isValidEmail(final String email) {
        if (email == null) {
            return false;
        }

        return email.matches(EMAIL_PATTERN);
    }

    public boolean isValidPostCount(final int postCount) {
        return postCount >= POST_MIN_COUNT && postCount <= POST_MAX_COUNT;
    }

    public boolean validate(final String name, final String password, final String email, final int postCount) {
        return isValidName(name) && isValidPassword(password) && isValidEmail(email) && isValidPostCount(postCount);
    }
}

