package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Действия")
class ActionTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Действие доступно с токеном прошедшим LOGIN")
    void shouldPerformActionAfterLogin() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);

        Response response = endpointClient.action(token);

        ResponseAssertions.assertStatus(response, 200);
        ResponseAssertions.assertOk(response);
    }

    @Test
    @DisplayName("Действие не доступно с токеном не прошедшим LOGIN")
    void shouldFailActionWithoutLogin() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.action(token);

        ResponseAssertions.assertStatus(response, 403);
        ResponseAssertions.assertError(response, "not found");
    }

    @Test
    @DisplayName("Действие не доступно с токеном после LOGOUT")
    void shouldFailActionAfterLogout() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        endpointClient.logout(token);

        Response response = endpointClient.action(token);

        ResponseAssertions.assertStatus(response, 403);
        ResponseAssertions.assertError(response, "not found");
    }
}