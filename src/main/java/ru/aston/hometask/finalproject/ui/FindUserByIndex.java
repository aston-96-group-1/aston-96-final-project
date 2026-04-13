package ru.aston.hometask.finalproject.ui;

import java.util.List;

import ru.aston.hometask.finalproject.constants.StateSign;
import ru.aston.hometask.finalproject.context.AppContext;
import ru.aston.hometask.finalproject.context.SessionContext;
import ru.aston.hometask.finalproject.models.User;

public class FindUserByIndex implements IMenuEntry {

    private final SessionContext sessionContext;
    private final AppContext appContext;

    public FindUserByIndex(final SessionContext sessionContext, final AppContext appContext) {
        this.sessionContext = sessionContext;
        this.appContext = appContext;
    }

    @Override
    public String getDescription() {
        return "Найти пользователя по индексу";
    }

    @Override
    public String getState() {
        return sessionContext.isShowUsersReady() ? StateSign.READY.getSign() : StateSign.NOT_READY.getSign();

    }

    @Override
    public void execute() {
        List<User> users = sessionContext.getUsers();
        if (users == null || users.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }

        System.out.println("Поиск пользователей по индексу\nВсего пользователей: " + users.size());
        System.out.println("Введите индекс от 1 до " + users.size());

        Integer indexUserNumber;

        try {
            indexUserNumber = appContext.getScanner().nextInt();
        } catch (Exception e) {
            System.out.println("Ошибка! Введите число");
            return;
        }
        if (indexUserNumber < 1 || indexUserNumber >= users.size()) {
            System.out.println("Неправильный диапазон, должен быть от 1 до " + users.size());
            return;
        }

        User user = users.get(indexUserNumber - 1);

        System.out.println("Пользователь с индексом " + indexUserNumber + ":");
        System.out.println(
                "Имя " + user.getName() + "\nemail: " + user.getEmail() + "\nАктивность: " + user.getPostCount());

    }
}