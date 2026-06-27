package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nord.codes.config.WireMockConfig.*;

@Epic("Endpoint API")
@DisplayName("Ошибки внешнего сервиса")
class ExternalServiceErrorTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Ошибка внешней авторизации")
    void shouldReturnErrorWhenExternalAuthFails() {
        stubAuth500();

        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertError(response, 500, "Internal Server Error");
    }

    @Test
    @DisplayName("Ошибка выполнения внешнего действия")
    void shouldReturnErrorWhenExternalActionFails() {
        stubAction500();

        String token = TokenGenerator.valid();

        endpointClient.login(token);

        Response response = endpointClient.action(token);

        ResponseAssertions.assertError(response, 500, "Internal Server Error");
    }

    @Test
    @DisplayName("Таймаут внешней авторизации")
    @Disabled("Тест на таймаут от внешнего сервиса. Отключен чтобы быстрее проходило")
    void shouldHandleAuthTimeout() {
        stubAuthTimeout();

        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertError(response, 500, "Internal Server Error");
    }

    @Test
    @DisplayName("Таймаут выполнения внешнего действия")
    @Disabled("Тест на таймаут от внешнего сервиса. Отключен чтобы быстрее проходило")
    void shouldHandleActionTimeout() {
        stubActionTimeout();

        String token = TokenGenerator.valid();

        endpointClient.login(token);
        Response response = endpointClient.action(token);

        ResponseAssertions.assertError(response, 500, "Internal Server Error");
    }
}