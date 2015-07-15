/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.lifefyre;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author jbjohn
 */
@Ignore
public class ApiLiveFyreTest {
    
    public ApiLiveFyreTest() {
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
     * Test of getResponseObject method, of class ApiLiveFyre.
     */
    @Test
    public void testGetResponseObject() {
        System.out.println("getResponseObject");
        HttpServletRequest request = null;
        ApiLiveFyre instance = new ApiLiveFyre();
        Map<String, Object> result = instance.getResponseObject(request);
        System.out.println(result.toString());
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
