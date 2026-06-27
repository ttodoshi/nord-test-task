package com.nord.codes.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class EndpointClient {
    private static final String ENDPOINT = "/endpoint";

    @Step("Отправка запроса на действие {2} с xApiKey={0}")
    public Response send(String xApiKey, String token, String action) {
        return given()
                .contentType(ContentType.URLENC)
                .header("X-Api-Key", xApiKey)
                .formParam("token", token)
                .formParam("action", action)
                .when()
                .post(ENDPOINT);
    }

    @Step("Отправка {1} запроса")
    public Response send(String token, String action) {
        return given()
                .contentType(ContentType.URLENC)
                .header("X-Api-Key", "qazWSXedc")
                .formParam("token", token)
                .formParam("action", action)
                .when()
                .post(ENDPOINT);
    }

    public Response login(String token) {
        return send(token, "LOGIN");
    }

    public Response action(String token) {
        return send(token, "ACTION");
    }

    public Response logout(String token) {
        return send(token, "LOGOUT");
    }
}