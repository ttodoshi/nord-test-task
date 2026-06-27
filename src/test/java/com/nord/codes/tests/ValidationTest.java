package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nord.codes.dto.Action.LOGIN;

@Epic("Endpoint API")
@DisplayName("Валидации")
class ValidationTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Запрос без X-Api-Key отклоняется")
    void shouldRejectWithoutApiKey() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRequest(null, token, LOGIN);

        ResponseAssertions.assertError(response, 401, "Missing or invalid API Key");
    }

    @Test
    @DisplayName("Запрос без token отклоняется")
    void shouldRejectWithoutToken() {
        Response response = endpointClient.sendRequest(null, LOGIN);

        ResponseAssertions.assertError(response, 400, "token: must not be null");
    }

    @Test
    @DisplayName("Запрос без action отклоняется")
    void shouldRejectWithoutAction() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRequest(token, null);

        ResponseAssertions.assertError(response, 400, "Allowed: LOGIN, LOGOUT, ACTION");
    }

    @Test
    @DisplayName("Невалидный X-Api-Key отклоняется")
    void shouldRejectInvalidApiKey() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRequest("WRONG_KEY", token, LOGIN);

        ResponseAssertions.assertError(response, 401, "Missing or invalid API Key");
    }

    @Test
    @DisplayName("Невалидный action отклоняется")
    void shouldRejectInvalidAction() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.sendRawRequest(token, "WRONG_ACTION");

        ResponseAssertions.assertError(response, 400, "Allowed: LOGIN, LOGOUT, ACTION");
    }

    @Test
    @DisplayName("Слишком короткий token отклоняется")
    void shouldRejectTooShortToken() {
        Response response = endpointClient.login(TokenGenerator.tooShort());

        ResponseAssertions.assertError(response, 400, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Слишком длинный token отклоняется")
    void shouldRejectTooLongToken() {
        Response response = endpointClient.login(TokenGenerator.tooLong());

        ResponseAssertions.assertError(response, 400, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Token в нижнем регистре отклоняется")
    void shouldRejectLowerCaseToken() {
        Response response = endpointClient.login(TokenGenerator.lowercase());

        ResponseAssertions.assertError(response, 400, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Token со спецсимволами отклоняется")
    void shouldRejectSpecialCharactersToken() {
        Response response = endpointClient.login(TokenGenerator.withSymbols());

        ResponseAssertions.assertError(response, 400, "token: must match \"^[0-9A-F]{32}$\"");
    }

    @Test
    @DisplayName("Пустой токен отклоняется")
    void shouldRejectEmptyToken() {
        Response response = endpointClient.login(TokenGenerator.blank());

        ResponseAssertions.assertError(response, 400, "token: must match \"^[0-9A-F]{32}$\"");
    }
}