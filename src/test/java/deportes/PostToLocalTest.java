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
public class PostToLocalTest {

    public PostToLocalTest() {
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
     * Test of postToServer method, of class PostToLocal.
     */
    @Test
    public void testPostToServerBoolean() throws Exception {
        System.out.println("postToServer");
        boolean mcpId = true;
        Integer counter = 10;
        while (counter > 0) {
            PostToLocal.postToServer(mcpId);
            counter--;
        }
    }
}
