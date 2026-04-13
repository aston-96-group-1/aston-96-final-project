package ru.aston.hometask.finalproject.constants;

public enum Strings {
    MAIN_MENU_TITLE("ГЛАВНОЕ МЕНЮ"),
    USER_COUNTER_MENU_TITLE("Подсчет числа вхождения пользователя в список"),
    PICK_SORT_MENU_TITLE("Выбрать сортировку"),
    SORT_MENU_TITLE("Отсортировать пользователей"),
    SHOW_USERS_MENU_TITLE("Показать текущих пользователей"),
    PROVIDER_MENU_TITLE("Выбрать провайдера"),
    PROVIDER_CHOOSER_TITLE("Выберите провайдера"),
    LOAD_USERS_MENU_TITLE("Загрузить пользователей"),
    EXIT_MENU_TITLE("Выход"),
    SORT_CHOOSER_TITLE("Выберите сортировку"),
    SORT_BY_EMAIL_TITLE("Сортировка по email."),

    SORT_ORDER_INPUT_PROMPT("Выберите порядок сортировки"),

    USER_INDEX_PROMPT("Введите индекс пользователя:"),
    USER_SIZE_INPUT_PROMPT("Введите размер:"),

    ERROR_SORT_NOTFOUND("Сортировки не существует"),
    ERROR_USER_INDEX("Индекс должен быть в промежутке от 0 до %d"),
    ERROR_USERS_NOT_LOADED("Пользователи не загруженны"),
    ERROR_USERS_SORT_NOT_READY("Необходимо выбрать пользователей, сортировку и порядок сортировки"),
    ERROR_PROVIDER_NOTFOUND("Провайдера не существует"),

    ERROR_PROVIDER_AND_SIZE("Не выбран провайдер или размер списка"),
    ERROR_SIZE_UNSUPPORTED("Выбранный вами провайдер не поддерживает указанный вами размер"),

    DATA_LOGGED_TO("Данные записаны в \"%s\"");

    private final String str;

    Strings(final String str) {
        this.str = str;
    }

    public String get() {
        return str;
    }
}
