package com.nord.codes.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WireMockConfig {
    private static final int WIREMOCK_PORT = 8888;
    private static final WireMockServer wireMockServer = new WireMockServer(WIREMOCK_PORT);

    private static final int TIMEOUT_MS = 11_000;

    public static void startWireMock() {
        wireMockServer.start();
        WireMock.configureFor(wireMockServer.port());
    }

    public static void resetDefaultState() {
        wireMockServer.resetAll();
        stubAuthOk();
        stubActionOk();
    }

    public static void stopWireMock() {
        wireMockServer.stop();
    }

    public static void stubAuthOk() {
        stubFor(post(urlEqualTo("/auth"))
                .withHeader("Content-Type", containing("application/x-www-form-urlencoded"))
                .withHeader("Accept", containing("application/json"))
                .withRequestBody(matching(".*token=.*"))
                .willReturn(aResponse()
                        .withStatus(200))
        );
    }

    public static void stubActionOk() {
        stubFor(post(urlEqualTo("/doAction"))
                .withHeader("Content-Type", containing("application/x-www-form-urlencoded"))
                .withHeader("Accept", containing("application/json"))
                .withRequestBody(matching(".*token=.*"))
                .willReturn(aResponse()
                        .withStatus(200))
        );
    }

    public static void stubAuth500() {
        stubFor(post(urlEqualTo("/auth"))
                .willReturn(serverError()));
    }

    public static void stubAction500() {
        stubFor(post(urlEqualTo("/doAction"))
                .willReturn(serverError()));
    }

    public static void stubAuthTimeout() {
        stubFor(post(urlEqualTo("/auth"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(TIMEOUT_MS)));
    }

    public static void stubActionTimeout() {
        stubFor(post(urlEqualTo("/doAction"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withFixedDelay(TIMEOUT_MS)));
    }
}