package com.jbjohn.deportes;

import org.junit.Test;
import java.io.IOException;

/**
 */
public class StandaloneTest {

    @Test
    public void testProcess() {
        Standalone standalone = new Standalone();
        try {
            standalone.process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}