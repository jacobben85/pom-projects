/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.ooyalaIngestion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jbjohn
 */
public class ClosedCaptionsTest {

    public ClosedCaptionsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of fetchClosedCaptions method, of class ClosedCaptions.
     */
    @Test
    public void testFetchClosedCaptions() {
        System.out.println("fetchClosedCaptions");
        ClosedCaptions instance = new ClosedCaptions();
        instance.fetchClosedCaptions();
    }

}
