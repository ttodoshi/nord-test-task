package com.nord.codes.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static io.restassured.RestAssured.given;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WireMockHelper {
    private static final String BASE = "http://localhost:8888/__admin";

    public static void resetMappings() {
        given()
                .noFilters()
                .when()
                .post(BASE + "/mappings/reset")
                .then()
                .statusCode(200);
    }

    public static void stubAuth500() {
        given()
                .noFilters()
                .contentType("application/json")
                .body("""
                        {
                          "request": {
                            "method": "POST",
                            "url": "/auth"
                          },
                          "response": {
                            "status": 500
                          }
                        }
                        """)
                .when()
                .post(BASE + "/mappings")
                .then()
                .statusCode(201);
    }

    public static void stubAction500() {
        given()
                .noFilters()
                .contentType("application/json")
                .body("""
                        {
                          "request": {
                            "method": "POST",
                            "url": "/doAction",
                            "headers": {
                              "Content-Type": {
                                "contains": "application/x-www-form-urlencoded"
                              }
                            }
                          },
                          "response": {
                            "status": 500
                          }
                        }
                        """)
                .when()
                .post(BASE + "/mappings")
                .then()
                .statusCode(201);
    }

    public static void stubAuthTimeout() {
        given()
                .noFilters()
                .body("""
                        {
                          "request": {
                            "method": "POST",
                            "url": "/auth"
                          },
                          "response": {
                            "status": 200,
                            "fixedDelayMilliseconds": 12000
                          }
                        }
                        """)
                .when()
                .post(BASE + "/mappings")
                .then()
                .statusCode(201);
    }
}