package com.jbjohn.deportes;

import org.junit.Test;

import java.io.IOException;

/**
 */
public class StandaloneTest {

    @Test
    public void testProcess() {
        Standalone standalone = new Standalone();
        try {

            String eventId = "832457";
            String startTime = "20150922T010000-0400";
            String endTime = "20150924T000000-0400";

            String fixtureKeys = "event-stats,event-stats-progressive";
            String url = "http://feed5.xmlteam.com/api/feeds?" +
                    "start=" + startTime +
                    "&end=" + endTime +
                    "&publisher-keys=optasports.com" +
                    "&sport-keys=15054000" +
                    "&fixture-keys=" + fixtureKeys +
                    "&format=xml" +
                    "&event-keys=EFBO";
            standalone.fetchLinksAndProcess(url + eventId, eventId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}