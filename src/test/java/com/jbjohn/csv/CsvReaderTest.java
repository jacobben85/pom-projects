/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author jbjohn
 */
@Ignore
public class CsvReaderTest {

    public CsvReaderTest() {
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
     * Test of readUserList method, of class CsvReader.
     */
    @Test
    public void testReadUserList() {
        System.out.println("readUserList");
        CsvReader instance = new CsvReader();
        instance.readUserList();
        // TODO review the generated test code and remove the default call to fail.
    }
}
