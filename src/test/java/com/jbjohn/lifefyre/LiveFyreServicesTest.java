/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.lifefyre;

import com.google.gson.JsonObject;
import com.livefyre.core.Collection;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LiveFyreServicesTest {

    public LiveFyreServicesTest() {
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
     * Test of updateCollectionRule method, of class LiveFyreServices.
     */
    @Test
    public void testUpdateCollectionRule() {
        System.out.println("updateCollectionRule");
        LiveFyreServices instance = new LiveFyreServices();
        instance.updateCollectionRule();
        // TODO review the generated test code and remove the default call to fail.
    }

    public void testGetExistingRules() {
        try {
            System.out.println("updateCollectionRule");
            LiveFyreServices instance = new LiveFyreServices();
            Collection collection  = instance.getCollection("Premios Juventud", "5ab801a9-a433-4a3a-b14a-8f8ae0070d81", "http://www.local.cms.uvn.io/entretenimiento/chismes");
            JsonObject rules = instance.getExistingRules(collection);
            //instance.saveExistingRules(rules);
            // TODO review the generated test code and remove the default call to fail.
        } catch (URISyntaxException ex) {
            System.out.println(ex.toString());
        }
    }

    public void testBuildJsonRequest() {
        System.out.println("building json request");
        LiveFyreServices instance = new LiveFyreServices();
        JsonObject json = instance.buildJsonRequest("collectionId", "articleid");
        System.out.println(json.toString());
    }
}
