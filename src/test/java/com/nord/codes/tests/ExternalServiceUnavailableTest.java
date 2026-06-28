package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.assertions.WireMockAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.nord.codes.config.WireMockConfig.stubActionNetworkFault;
import static com.nord.codes.config.WireMockConfig.stubAuthNetworkFault;

@Epic("Endpoint API")
@Feature("Интеграция с внешним сервисом")
@Story("Сетевые сбои (сервис недоступен)")
@DisplayName("Недоступность внешнего сервиса")
class ExternalServiceUnavailableTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Ошибка внешней авторизации (сервер недоступен)")
    @SneakyThrows
    void shouldHandleExternalAuthUnavailable() {
        stubAuthNetworkFault();

        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertError(
                response,
                500,
                "Internal Server Error"
        );
        WireMockAssertions.assertAuthCalls(1, token);
    }

    @Test
    @DisplayName("Ошибка внешнего действия (сервер недоступен)")
    @SneakyThrows
    void shouldHandleExternalActionUnavailable() {
        String token = TokenGenerator.valid();

        Response loginResponse = endpointClient.login(token);
        ResponseAssertions.assertOk(loginResponse);

        stubActionNetworkFault();

        Response response = endpointClient.action(token);

        ResponseAssertions.assertError(
                response,
                500,
                "Internal Server Error"
        );
        WireMockAssertions.assertActionCalls(1, token);
    }
}