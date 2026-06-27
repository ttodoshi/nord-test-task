package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("Endpoint API")
@DisplayName("Выполнение действия")
class ActionTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("ACTION доступен после успешного LOGIN")
    void shouldPerformActionAfterLogin() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);

        Response response = endpointClient.action(token);

        ResponseAssertions.assertOk(response);
    }

    @Test
    @DisplayName("ACTION доступен для многократного вызова после успешного LOGIN")
    void shouldAllowMultipleAction() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);

        Response response = endpointClient.action(token);
        ResponseAssertions.assertOk(response);
        response = endpointClient.action(token);
        ResponseAssertions.assertOk(response);
    }

    @Test
    @DisplayName("ACTION без LOGIN запрещен")
    void shouldFailActionWithoutLogin() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.action(token);

        ResponseAssertions.assertError(response, 403, "not found");
    }

    @Test
    @DisplayName("ACTION после LOGOUT запрещен")
    void shouldFailActionAfterLogout() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        endpointClient.logout(token);

        Response response = endpointClient.action(token);

        ResponseAssertions.assertError(response, 403, "not found");
    }
}