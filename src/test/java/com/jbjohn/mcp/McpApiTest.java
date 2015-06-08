/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.mcp;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jbjohn
 */
public class McpApiTest {

    public McpApiTest() {
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
     * Test of getMcpPrograms method, of class McpApi.
     */
    public void testGetMcpPrograms() {
        System.out.println("getMcpPrograms");
        HashMap<String, String> result = McpApi.getMcpPrograms();
        McpApi.printHashMap(result);
    }

    @Test
    public void testGetMcpSyndicatedIds() {
        McpApi.getMcpSyndicatedIds();
        McpApi.printContent();
    }
}
