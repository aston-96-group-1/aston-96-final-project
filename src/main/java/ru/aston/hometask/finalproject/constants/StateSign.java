package ru.aston.hometask.finalproject.constants;

public enum StateSign {
    NOT_READY("❌"),
    READY("✅");

    private final String sign;

    StateSign(final String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
