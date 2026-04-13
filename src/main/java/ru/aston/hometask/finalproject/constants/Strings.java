package ru.aston.hometask.finalproject.constants;

public enum Strings {
    MAIN_MENU_TITLE("\n=== ГЛАВНОЕ МЕНЮ ===\n"),
    USER_COUNTER_MENU_TITLE("Подсчет числа вхождения пользователя в список"),
    PICK_SORT_MENU_TITLE("Выбрать сортировку"),
    SORT_MENU_TITLE("Отсортировать пользователей"),
    SHOW_USERS_MENU_TITLE("Показать текущих пользователей"),
    PROVIDER_MENU_TITLE("Выбрать провайдера"),
    PROVIDER_CHOOSER_TITLE("Выберите провайдера"),
    LOAD_USERS_MENU_TITLE("Загрузить пользователей"),
    EXIT_MENU_TITLE("Выход"),
    SORT_CHOOSER_TITLE("Выберите сортировку"),
    USER_INPUT_TITLE("\n=== ввод пользователя #%d ===\n"),

    FILE_USER_PROVIDER_TITLE("Заполнение списка пользователей из файла."),
    RANDOM_USER_PROVIDER_TITLE("Заполнение списка пользователей случайным образом."),
    MANUAL_USER_PROVIDER_TITLE("Ручной ввод пользователей."),

    SORT_BY_EMAIL_TITLE("Сортировка по email."),
    SORT_BY_NAME_TITLE("Сортировка по имени."),
    SORT_BY_POST_COUNT("Сортировка по количеству постов."),
    SORT_BY_POST_COUNT_EVEN_ONLY("Сортировка по количеству постов (только четные значения)."),

    SORT_ORDER_INPUT_PROMPT("Выберите порядок сортировки"),

    USER_INDEX_PROMPT("Введите индекс пользователя:"),
    USER_SIZE_INPUT_PROMPT("Введите размер:"),
    FILE_PATH_INPUT_PROMPT("Введите путь к файлу с пользователями:"),
    POST_COUNT_PROMPT("Количество постов: "),
    EMAIL_PROMPT("Email: "),
    PASSWORD_PROMPT("Пароль: "),
    NAME_PROMPT("Имя: "),

    ERROR_INVALID_MENU_ENTRY("Данного пункта не существует, попробуйте еще раз:"),
    ERROR_FILE_SIZE_LESS_THAN_NEEDED("Пользователей в файле меньше заданного. Size User: %d"),
    ERROR_FILE_READ_FAILED("Ошибка чтения файла!"),
    ERROR_FILE_NOT_VALID("Файл не найден: %s"),

    ERROR_OBJECT_IS_NULL("Must not be null!"),

    ERROR_INPUT_MUST_BE_NUMBER("Необходимо ввести число"),

    ERROR_SORT_NOTFOUND("Сортировки не существует"),
    ERROR_USER_INDEX("Индекс должен быть в промежутке от 0 до %d"),
    ERROR_USERS_NOT_LOADED("Пользователи не загруженны"),
    ERROR_USERS_SORT_NOT_READY("Необходимо выбрать пользователей, сортировку и порядок сортировки"),
    ERROR_PROVIDER_NOTFOUND("Провайдера не существует"),

    ERROR_NAME_FORMAT("Имя должно содержать только латинские буквы и цифры."),
    ERROR_PASSWORD_FORMAT("Пароль должен быть от %d до %d символов"),
    ERROR_EMAIL_FORMAT("Некорректный email."),
    ERROR_POST_COUNT_FORMAT("Введите число от от %d до %d"),

    ERROR_PROVIDER_AND_SIZE("Не выбран провайдер или размер списка"),
    ERROR_SIZE_UNSUPPORTED("Выбранный вами провайдер не поддерживает указанный вами размер"),
    ERROR_SIZE_IS_NEGATIVE("Размер списка не может быть отрицательным!"),

    DATA_LOGGED_TO("Данные записаны в \"%s\""),

    TRY_AGAIN("Попробуйте ещё раз:"),
    USERS("Пользователи"),
    PRESS_ENTER("Нажмите ENTER для продолжения..."),
    USER_COUNTER_OUTPUT("\nКоличество вхождений элемента User{%s}  в коллекцию: %d.\n");

    private final String str;

    Strings(final String str) {
        this.str = str;
    }

    public String get() {
        return str;
    }
}
