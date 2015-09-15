package com.jbjohn.deportes;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static com.jayway.awaitility.Awaitility.await;

/**
 * Test file
 * Created by jbjohn on 9/15/15.
 */
@RunWith(Parameterized.class)
public class FeedProcessingTest {

    private static FeedProcessing fp;
    private static String expected;
    private static String type = "stats";
    private static String eventId = "798344";

    @BeforeClass
    public static void setUp() throws IOException, URISyntaxException {
        fp = new FeedProcessing();
        expected = fp.processFeed(type, eventId);
        System.out.println("re-run setup");
    }

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[5400][0]);
    }

    @Test
    public void testProcessFeed() throws InterruptedException {
        FeedProcessing fp = new FeedProcessing();
        String response = null;
        try {
            response = fp.processFeed(type, eventId);
            JSONAssert.assertEquals(expected, response, false);
        } catch (AssertionError e) {
            if (response != null) {
                expected = response;
            }
            throw new AssertionError(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        await().until(() ->
        {
            return delayMethod();
        });
    }

    private boolean delayMethod() throws InterruptedException {
        Thread.sleep(1000);
        return true;
    }
}