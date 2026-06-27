package com.nord.codes.tests.base;

import com.nord.codes.config.RestAssuredConfig;
import com.nord.codes.config.WireMockConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    @BeforeAll
    static void beforeAll() {
        RestAssuredConfig.configure();
        WireMockConfig.startWireMock();
    }

    @BeforeEach
    void beforeEach() {
        WireMockConfig.resetDefaultState();
    }

    @AfterAll
    static void afterAll() {
        WireMockConfig.stopWireMock();
    }
}