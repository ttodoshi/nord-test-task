package com.nord.codes.assertions;

import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WireMockAssertions {
    @Step("Проверить, что был отправлен {expectedCount} запрос(а) к /auth")
    public static void assertAuthCalls(int expectedCount, String token) {
        try {
            verify(expectedCount, postRequestedFor(urlEqualTo("/auth"))
                    .withRequestBody(matching(".*token=" + token + ".*")));
        } catch (AssertionError e) {
            throw new AssertionError(
                    """
                            Ожидалось %d запрос(а) к /auth с токеном %s
                            Фактическое количество:
                            %s
                            """.formatted(expectedCount, token, e.getMessage()),
                    e
            );
        }
    }

    @Step("Проверить, что был отправлен {expectedCount} запрос(а) к /doAction")
    public static void assertActionCalls(int expectedCount, String token) {
        try {
            verify(expectedCount, postRequestedFor(urlEqualTo("/doAction"))
                    .withRequestBody(matching(".*token=" + token + ".*")));
        } catch (AssertionError e) {
            throw new AssertionError(
                    """
                            Ожидалось %d запрос(а) к /doAction с токеном %s
                            Фактическое количество:
                            %s
                            """.formatted(expectedCount, token, e.getMessage()),
                    e
            );
        }
    }
}