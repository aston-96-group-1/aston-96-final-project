package ru.aston.hometask.finalproject.validation;

public class Validator {
    public boolean isValidPassword(final String password) {
        // TODO: Реализовать метод валидации пароля

        return false;
    }

    public boolean isValidEmail(final String email) {
        // TODO: Реализовать метод валидации email

        return false;
    }

    public boolean isValidPostCount(final int postCount) {
        // TODO: Реализовать метод валидации количества постов

        return false;
    }

    public boolean validate(final String password, final String email, final int postCount ) {
        return isValidPassword(password) && isValidEmail(email) && isValidPostCount(postCount);
    }
}
