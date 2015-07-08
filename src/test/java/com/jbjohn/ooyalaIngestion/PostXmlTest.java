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

/**
 *
 * @author jbjohn
 */
public class PostXmlTest {

    public PostXmlTest() {
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
     * Test of postToServer method, of class PostXml.
     * @throws java.lang.Exception
     */
    @Test
    public void testPostToServer() throws Exception {
        System.out.println("postToServer");
        Integer counter = 1;
        while (counter > 0) {

            PostXml.postToServer("3012384");
            PostXml.postToServer("3012779");
            PostXml.postToServer("3012790");

            PostXml.postToServer("3012794");
            PostXml.postToServer("3057907"); // smil file missing
            PostXml.postToServer("3072707");

            PostXml.postToServer("3072870");
            PostXml.postToServer("3073230");
            PostXml.postToServer("3074108");

            PostXml.postToServer("3074113");
            counter--;
        }
    }

}
