package ru.aston.hometask.finalproject.validation;

public class Validator {
    public boolean isValidName(final String name) {
        // TODO: Реализовать метод валидации имени пользователя

        return false;
    }

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

    public boolean validate(final String name, final String password, final String email, final int postCount) {
        return isValidName(name) && isValidPassword(password) && isValidEmail(email) && isValidPostCount(postCount);
    }
}
