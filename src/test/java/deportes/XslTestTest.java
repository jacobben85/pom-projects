/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deportes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jbjohn
 */
public class XslTestTest {

    public XslTestTest() {
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
     * Test of process method, of class XslTest.
     */
    @Test
    public void testProcess() {
        System.out.println("process");
        String xmlt = "testing";
        String xslt = "testing";
        XslTest instance = new XslTest();
        boolean expResult = false;
        boolean result = instance.process(xmlt, xslt);
    }

}
