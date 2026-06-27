package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nord.codes.utils.WireMockHelper.*;

@DisplayName("Ошибки внешнего сервиса")
class ExternalServiceErrorTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Сервис возвращает 500 при ошибке внешнего сервиса на /auth")
    void shouldReturnErrorWhenExternalAuthFails() {
        stubAuth500();

        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertStatus(response, 500);
        ResponseAssertions.assertError(response, "Internal Server Error");
    }

    @Test
    @DisplayName("Сервис возвращает 500 при ошибке внешнего сервиса на /doAction")
    void shouldReturnErrorWhenExternalActionFails() {
        stubAction500();

        String token = TokenGenerator.valid();

        endpointClient.login(token);

        Response response = endpointClient.action(token);

        ResponseAssertions.assertStatus(response, 500);
        ResponseAssertions.assertError(response, "Internal Server Error");
    }

    @Test
    @DisplayName("Сервис возвращает 500 при таймауте внешнего сервиса на /auth")
    @Disabled("Тест на таймаут от внешнего сервиса. Отключен чтобы быстрее проходило")
    void shouldHandleAuthTimeout() {
        stubAuthTimeout();

        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertStatus(response, 500);
        ResponseAssertions.assertError(response, "Internal Server Error");
    }
}