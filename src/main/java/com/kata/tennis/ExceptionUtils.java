package com.kata.tennis;

public interface ExceptionUtils {
    static <T> void isNotNull(final T obj, final String message) {
        if (obj == null) {
            throw new RuntimeException(message);
        }
    }

    static <T> void isTrue(final boolean condition, final String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static <T> void isFalse(final boolean condition, final String message) {
        if (condition) {
            throw new RuntimeException(message);
        }
    }
}
