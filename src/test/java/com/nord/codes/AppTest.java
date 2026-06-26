package com.nord.codes;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test :-)
     */
    @Test
    void shouldAnswerWithTrue() throws Exception {
        PrintStream sdtOut = System.out;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintStream printStream = new PrintStream(out, true)) {
            System.setOut(printStream);
            App.main(new String[]{});
            assertEquals("Hello World!\n", out.toString());
        } finally {
            System.setOut(sdtOut);
        }
    }
}