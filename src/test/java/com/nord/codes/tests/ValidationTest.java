package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.dto.Action;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@Epic("Endpoint API")
@Feature("Валидация входящих запросов")
@DisplayName("Валидации входящих запросов")
class ValidationTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @ParameterizedTest
    @MethodSource("invalidTokenProvider")
    @DisplayName("Невалидный токен отклоняется")
    void shouldRejectInvalidToken(String invalidToken) {
        Response response = endpointClient.login(invalidToken);

        ResponseAssertions.assertError(response, 400, "token: must match \"^[0-9A-F]{32}$\"");
    }

    private static Stream<Arguments> invalidTokenProvider() {
        return Stream.of(
                Arguments.of(Named.of("Слишком короткий токен", TokenGenerator.tooShort())),
                Arguments.of(Named.of("Слишком длинный токен", TokenGenerator.tooLong())),
                Arguments.of(Named.of("Токен в нижнем регистре", TokenGenerator.lowercase())),
                Arguments.of(Named.of("Токен со спецсимволами", TokenGenerator.withSymbols())),
                Arguments.of(Named.of("Пустой токен", TokenGenerator.blank()))
        );
    }

    @Test
    @DisplayName("Запрос без token отклоняется")
    void shouldRejectWithoutToken() {
        Response response = endpointClient.sendRequest(null, Action.LOGIN);

        ResponseAssertions.assertError(response, 400, "token: must not be null");
    }

    @Test
    @DisplayName("Запрос без X-Api-Key отклоняется")
    void shouldRejectWithoutApiKey() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRequest(null, token, Action.LOGIN);

        ResponseAssertions.assertError(response, 401, "Missing or invalid API Key");
    }

    @Test
    @DisplayName("Невалидный X-Api-Key отклоняется")
    void shouldRejectInvalidApiKey() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRequest("WRONG_KEY", token, Action.LOGIN);

        ResponseAssertions.assertError(response, 401, "Missing or invalid API Key");
    }

    @Test
    @DisplayName("Запрос без action отклоняется")
    void shouldRejectWithoutAction() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRequest(token, null);

        ResponseAssertions.assertError(response, 400, "Allowed: LOGIN, LOGOUT, ACTION");
    }

    @Test
    @DisplayName("Невалидный action отклоняется")
    void shouldRejectInvalidAction() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRawRequest(token, "WRONG_ACTION");

        ResponseAssertions.assertError(response, 400, "Allowed: LOGIN, LOGOUT, ACTION");
    }

    @Test
    @DisplayName("Action в нижнем регистре отклоняется")
    void shouldRejectLowercaseAction() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRawRequest(token, "login");

        ResponseAssertions.assertError(response, 400, "Allowed: LOGIN, LOGOUT, ACTION");
    }
}