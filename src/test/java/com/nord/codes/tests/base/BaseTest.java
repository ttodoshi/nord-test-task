package com.nord.codes.tests.base;

import com.nord.codes.config.RestAssuredConfig;
import com.nord.codes.utils.WireMockHelper;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    @BeforeAll
    static void beforeAll() {
        RestAssuredConfig.configure();
    }

    @BeforeEach
    void beforeEach() {
        WireMockHelper.resetMappings();
    }
}