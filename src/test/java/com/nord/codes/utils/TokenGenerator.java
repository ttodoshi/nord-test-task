package com.nord.codes.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Random;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TokenGenerator {
    private static final int TOKEN_LENGTH = 32;
    private static final String CHARS = "0123456789ABCDEF";
    private static final Random RANDOM = new Random();

    public static String valid() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            builder.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }
        return builder.toString();
    }

    public static String tooShort() {
        return "ABC123";
    }

    public static String tooLong() {
        return valid() + "AA";
    }

    public static String lowercase() {
        return valid().toLowerCase();
    }

    public static String withSymbols() {
        return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!";
    }

    public static String blank() {
        return "   ";
    }
}