package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.assertions.WireMockAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Endpoint API")
@Feature("Управление сессиями")
@DisplayName("Логин и выход")
class SessionsTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Успешный LOGIN с валидным токеном")
    void shouldLoginSuccessfully() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertOk(response);
        WireMockAssertions.assertAuthCalls(1, token);
    }

    @Test
    @DisplayName("Повторный LOGIN запрещен")
    void shouldNotAllowDoubleLogin() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        WireMockAssertions.assertAuthCalls(1, token);

        Response response = endpointClient.login(token);

        ResponseAssertions.assertError(response, 409, "already exists");
        WireMockAssertions.assertAuthCalls(1, token);
    }

    @Test
    @DisplayName("Успешный LOGOUT после LOGIN")
    void shouldLogoutSuccessfully() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        Response response = endpointClient.logout(token);

        ResponseAssertions.assertOk(response);
        WireMockAssertions.assertAuthCalls(1, token);
    }

    @Test
    @DisplayName("LOGOUT без LOGIN запрещен")
    void shouldNotAllowLogoutWithoutLogin() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.logout(token);

        ResponseAssertions.assertError(response, 403, "not found");
        WireMockAssertions.assertAuthCalls(0, token);
    }

    @Test
    @DisplayName("Повторный LOGOUT запрещен")
    void shouldNotAllowDoubleLogout() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        endpointClient.logout(token);
        Response response = endpointClient.logout(token);

        ResponseAssertions.assertError(response, 403, "not found");
    }
}