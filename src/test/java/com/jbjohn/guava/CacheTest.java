/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.guava;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Ignore;

/**
 *
 * @author jbjohn
 */
@Ignore
public class CacheTest {

    public CacheTest() {
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
     * Test of getEntry method, of class Cache.
     */
    @Test
    public void testGetEntry() {
        System.out.println("getEntry");
        String key = "testing";
        Cache instance = new Cache();
        instance.setEntry(key, key);
        String expResult = "testing";
        String result = (String) instance.getEntry(key);

        System.out.println(result);

        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

}
