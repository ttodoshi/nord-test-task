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
@Feature("Жизненный цикл сессии")
@DisplayName("Жизненный цикл сессии")
class SessionsLifecycleTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Токен можно использовать повторно после повторного LOGIN")
    void shouldAllowReuseTokenAfterRelogin() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        endpointClient.logout(token);

        Response loginResponse = endpointClient.login(token);
        ResponseAssertions.assertOk(loginResponse);

        Response actionResponse = endpointClient.action(token);
        ResponseAssertions.assertOk(actionResponse);

        WireMockAssertions.assertAuthCalls(2, token);
        WireMockAssertions.assertActionCalls(1, token);
    }

    @Test
    @DisplayName("Несколько пользователей работают независимо")
    void shouldHandleMultipleUsersIndependently() {
        String first = TokenGenerator.valid();
        String second = TokenGenerator.valid();

        endpointClient.login(first);
        endpointClient.login(second);

        ResponseAssertions.assertOk(endpointClient.action(first));
        ResponseAssertions.assertOk(endpointClient.action(second));

        endpointClient.logout(first);

        ResponseAssertions.assertError(
                endpointClient.action(first),
                403,
                "not found"
        );

        ResponseAssertions.assertOk(
                endpointClient.action(second)
        );

        WireMockAssertions.assertAuthCalls(1, first);
        WireMockAssertions.assertAuthCalls(1, second);

        WireMockAssertions.assertActionCalls(1, first);
        WireMockAssertions.assertActionCalls(2, second);
    }
}