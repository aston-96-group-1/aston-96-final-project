package ru.aston.hometask.finalproject.constants;

import java.util.List;

public enum SampleUserData {
    USER_NAMES(List.of("cat", "greg", "door", "aunt", "chair", "raskol", "harmony")),
    EMAIL_DOMAINS(List.of("gmail.com", "yandex.ru", "mail.ru", "example.com"));

    private final List<String> list;

    SampleUserData(final List<String> list) {
        this.list = list;
    }

    public List<String> getList() {
        return list;
    }
}
