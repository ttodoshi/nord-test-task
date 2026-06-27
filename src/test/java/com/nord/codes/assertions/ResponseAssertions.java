package com.nord.codes.assertions;

import com.nord.codes.dto.UserResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static org.junit.jupiter.api.Assertions.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseAssertions {
    @Step("Проверить успешный ответ сервиса")
    public static void assertOk(Response response) {
        assertStatus(response, 200);
        UserResponse userResponse = response.then().extract().as(UserResponse.class);
        assertEquals("OK", userResponse.result());
        assertNull(userResponse.message());
    }

    @Step("Результат ответа ERROR с сообщением {2}")
    public static void assertError(Response response, int statusCode, String messagePart) {
        assertStatus(response, statusCode);
        UserResponse userResponse = response.then().extract().as(UserResponse.class);
        assertEquals("ERROR", userResponse.result());
        assertNotNull(userResponse.message());
        assertTrue(userResponse.message().contains(messagePart));
    }

    @Step("Статус ответа {1}")
    private static void assertStatus(Response response, int statusCode) {
        assertEquals(statusCode, response.statusCode());
    }
}