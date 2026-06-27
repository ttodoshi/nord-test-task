package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Валидация значений")
class ValidationTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Сервис отклоняет невалидный X-Api-Key")
    void shouldRejectInvalidApiKey() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.send("WRONG_KEY", token, "LOGIN");

        ResponseAssertions.assertStatus(response, 401);
        ResponseAssertions.assertError(response, "Missing or invalid API Key");
    }

    @Test
    @DisplayName("Сервис отклоняет невалидный action")
    void shouldRejectInvalidAction() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.send(token, "WRONG_ACTION");

        ResponseAssertions.assertStatus(response, 400);
        ResponseAssertions.assertError(response, "Allowed: LOGIN, LOGOUT, ACTION");
    }

    @Test
    @DisplayName("Сервис возвращает сообщение об ошибке при слишком коротком токене")
    void shouldRejectTooShortToken() {
        Response response = endpointClient.login(TokenGenerator.tooShort());

        ResponseAssertions.assertStatus(response, 400);
        ResponseAssertions.assertError(response, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Сервис возвращает сообщение об ошибке при слишком длинном токене")
    void shouldRejectTooLongToken() {
        Response response = endpointClient.login(TokenGenerator.tooLong());

        ResponseAssertions.assertStatus(response, 400);
        ResponseAssertions.assertError(response, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Сервис возвращает сообщение об ошибке при токене с буквами нижнего регистра")
    void shouldRejectLowerCaseToken() {
        Response response = endpointClient.login(TokenGenerator.lowercase());

        ResponseAssertions.assertStatus(response, 400);
        ResponseAssertions.assertError(response, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Сервис возвращает сообщение об ошибке при токене со спецсимволами")
    void shouldRejectSpecialCharactersToken() {
        Response response = endpointClient.login(TokenGenerator.withSymbols());

        ResponseAssertions.assertStatus(response, 400);
        ResponseAssertions.assertError(response, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Сервис возвращает сообщение об ошибке при пустом токене")
    void shouldRejectEmptyToken() {
        Response response = endpointClient.login(TokenGenerator.empty());

        ResponseAssertions.assertStatus(response, 400);
        ResponseAssertions.assertError(response, "token: must match \"^[0-9A-F]{32}$\"");
    }
}