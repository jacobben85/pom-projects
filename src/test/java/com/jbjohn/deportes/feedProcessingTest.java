package com.jbjohn.deportes;

import org.junit.Test;
/**
 * Test file
 * Created by jbjohn on 9/15/15.
 */
public class FeedProcessingTest {

    @Test
    public void testProcessFeed() {
        FeedProcessing fp = new FeedProcessing();
        try {
            String commentary = fp.processFeed("commentary", "832626");
            String stats = fp.processFeed("stats", "832626");
            String statsLive = fp.processFeed("stats-live", "832626");

            System.out.println(commentary);
            System.out.println(stats);
            System.out.println(statsLive);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}