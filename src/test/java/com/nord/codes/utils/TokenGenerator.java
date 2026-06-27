package com.nord.codes.utils;

import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenGenerator {
    private static final String CHARS = "0123456789ABCDEF";
    private static final Random RANDOM = new Random();

    @Step("Генерация валидного токена")
    public static String valid() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            builder.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return builder.toString();
    }

    @Step("Генерация слишком короткого токена")
    public static String tooShort() {
        return "ABC123";
    }

    @Step("Генерация слишком длинного токена")
    public static String tooLong() {
        return valid() + "AA";
    }

    @Step("Генерация токена с буквами нижнего регистра")
    public static String lowercase() {
        return valid().toLowerCase();
    }

    @Step("Генерация токена со спецсимволами")
    public static String withSymbols() {
        return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!";
    }

    @Step("Генерация пустого токена")
    public static String empty() {
        return "   ";
    }
}