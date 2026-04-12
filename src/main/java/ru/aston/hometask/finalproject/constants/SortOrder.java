package ru.aston.hometask.finalproject.constants;

public enum SortOrder {
    ASC("1"),
    DESC("2");

    private final String key;

    SortOrder(final String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static SortOrder getByKey(final String key) {
        for (SortOrder order : values()) {
            if (order.key.equals(key)) {
                return order;
            }
        }
        return null;
    }
}
