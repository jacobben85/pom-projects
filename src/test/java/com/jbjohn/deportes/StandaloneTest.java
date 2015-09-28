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
            String eventId = "832461";
            String url = "http://feed5.xmlteam.com/api/feeds?" +
                    "start=20150922T000000-0500" +
                    "&end=20150924T230000-0500" +
                    "&publisher-keys=optasports.com" +
                    "&sport-keys=15054000" +
                    "&fixture-keys=event-stats,event-stats-progressive" +
                    "&format=xml" +
                    "&event-keys=EFBO";
            standalone.fetchLinksAndProcess(url + eventId, eventId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}