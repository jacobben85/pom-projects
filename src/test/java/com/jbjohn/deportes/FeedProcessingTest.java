package com.jbjohn.deportes;

import org.junit.Test;
/**
 * Created by jbjohn on 9/15/15.
 */
public class FeedProcessingTest {

    @Test
    public void testProcessFeed() {
        FeedProcessing fp = new FeedProcessing();
        try {
            fp.processFeed("test");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}