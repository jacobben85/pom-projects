package com.jbjohn.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Regex Utils tests
 * Created by jbjohn on 9/16/15.
 */
public class RegexUtilTest {

    RegexUtil regexUtil;

    @Before
    public void setUp() throws Exception {
        regexUtil = new RegexUtil();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testResponseString() {

        regexUtil.setString("event-stats-progressive");
        regexUtil.setPattern("-progressive");
        regexUtil.setReplacement("");

        String response = regexUtil.responseString();
        System.out.println(response);
    }
}