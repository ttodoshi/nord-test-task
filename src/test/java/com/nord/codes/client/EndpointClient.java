package com.nord.codes.client;

import com.nord.codes.dto.Action;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.nord.codes.dto.Action.*;
import static io.restassured.RestAssured.given;

public class EndpointClient {
    private static final String ENDPOINT = "/endpoint";
    private static final String X_API_KEY = System.getProperty("secret", "qazWSXedc");

    public Response login(String token) {
        return sendRequest(token, LOGIN);
    }

    public Response action(String token) {
        return sendRequest(token, ACTION);
    }

    public Response logout(String token) {
        return sendRequest(token, LOGOUT);
    }

    @Step("Отправка запрос с действием {2}")
    public Response sendRawRequest(String xApiKey, String token, String action) {
        var request = given()
                .contentType(ContentType.URLENC);
        if (xApiKey != null) {
            request.header("X-Api-Key", xApiKey);
        }
        if (token != null) {
            request.formParam("token", token);
        }
        if (action != null) {
            request.formParam("action", action);
        }
        return request
                .when()
                .post(ENDPOINT);
    }

    public Response sendRawRequest(String token, String action) {
        return sendRawRequest(X_API_KEY, token, action);
    }

    public Response sendRequest(String xApiKey, String token, Action action) {
        return sendRawRequest(
                xApiKey,
                token,
                action == null ? null : action.name()
        );
    }

    public Response sendRequest(String token, Action action) {
        return sendRequest(X_API_KEY, token, action);
    }
}