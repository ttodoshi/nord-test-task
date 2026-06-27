package com.nord.codes.tests;

import com.nord.codes.assertions.ResponseAssertions;
import com.nord.codes.client.EndpointClient;
import com.nord.codes.tests.base.BaseTest;
import com.nord.codes.utils.TokenGenerator;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Авторизация")
class AuthenticationTest extends BaseTest {
    private final EndpointClient endpointClient = new EndpointClient();

    @Test
    @DisplayName("Логин доступен с валидным токеном")
    void shouldLoginSuccessfully() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.login(token);

        ResponseAssertions.assertStatus(response, 200);
        ResponseAssertions.assertOk(response);
    }

    @Test
    @DisplayName("Логин не доступен с валидным токеном при повторной попытке")
    void shouldNotAllowDoubleLogin() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);

        Response response = endpointClient.login(token);

        ResponseAssertions.assertStatus(response, 409);
        ResponseAssertions.assertError(response, "already exists");
    }

    @Test
    @DisplayName("Логаут доступен после LOGIN")
    void shouldLogoutSuccessfully() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        Response response = endpointClient.logout(token);

        ResponseAssertions.assertStatus(response, 200);
        ResponseAssertions.assertOk(response);
    }

    @Test
    @DisplayName("Логаут не доступен без LOGIN")
    void shouldNotAllowLogoutWithoutLogin() {
        String token = TokenGenerator.valid();

        Response response = endpointClient.logout(token);

        ResponseAssertions.assertStatus(response, 403);
        ResponseAssertions.assertError(response, "not found");
    }

    @Test
    @DisplayName("Логаут не доступен при повторной попытке")
    void shouldNotAllowDoubleLogout() {
        String token = TokenGenerator.valid();

        endpointClient.login(token);
        endpointClient.logout(token);
        Response response = endpointClient.logout(token);

        ResponseAssertions.assertStatus(response, 403);
        ResponseAssertions.assertError(response, "not found");
    }
}