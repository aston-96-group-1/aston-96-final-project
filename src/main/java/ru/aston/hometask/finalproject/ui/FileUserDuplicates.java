package ru.aston.hometask.finalproject.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.models.User;

public class FileUserDuplicates implements IMenuEntry {

    private final SessionContext sessionContext;
    private final AppContext appContext;

    public FileUserDuplicates(final SessionContext sessionContext, final AppContext appContext) {

        this.sessionContext = sessionContext;
        this.appContext = appContext;

    }

    @Override
    public String getDescription() {
        return "Дубликаты пользователей";
    }

    @Override
    public String getState() {
        List<User> users = sessionContext.getUsers();
        if (users == null || users.isEmpty()) {
            return "Нет пользователей";
        }
        return "";
    }

    @Override
    public void execute() {
        List<User> users = sessionContext.getUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("Список пользователей пуст");
            return;
        }

        Map<String, List<User>> emailDuplicates = users.stream().collect(Collectors.groupingBy(User::getEmail))
                .entrySet().stream().filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, List<User>> nameDuplicates = users.stream().collect(Collectors.groupingBy(User::getName)).entrySet()
                .stream().filter(e -> e.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        String fileName = appContext.getDotenv().get("DUPLICATE_FILES");

        if (fileName == null || fileName.isBlank()) {
            fileName = "duplicates.json";
        }

        if (emailDuplicates.isEmpty() && nameDuplicates.isEmpty()) {
            System.out.println("Дубликатов нет");
            return;
        }

        appContext.getFileWriter().writeToFile(fileName, List.of(emailDuplicates, nameDuplicates));

        System.out.println("Дупликаты сохранены" + fileName);

    }

}
