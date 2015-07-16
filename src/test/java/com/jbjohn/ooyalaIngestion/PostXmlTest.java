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
        System.out.println("post To Server");
        Integer counter = 1;
        while (counter > 0) {

            PostXml.postToServer("3074113");
            PostXml.postToServer("3074115");

            counter--;
        }
    }

    @Test
    public void testPostToServerNeulion() throws Exception {
        System.out.println("post To Server Neulion");
        Integer counter = 1;
        while (counter > 0) {

            PostXml.postToServer("unidepp13072");
            PostXml.postToServer("unidepfh230133");
            counter--;
        }
    }

}
