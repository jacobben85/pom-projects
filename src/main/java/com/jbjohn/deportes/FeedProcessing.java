package com.jbjohn.deportes;

import com.jbjohn.deportes.utils.SignatureGenerator;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by jbjohn on 9/15/15.
 */
public class FeedProcessing {

    public void processFeed(String gameId) throws URISyntaxException, IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet();
        String url = "/feed/sports/soccer/event-commentary/832626";
        String signature = SignatureGenerator.generateSignature(url);
        System.out.println(signature);
        url = "http://sports.dev.y.univision.com" + url + "?client_id=" + SignatureGenerator.getClientId() + "&signature=" + signature;
        System.out.println(url);
        URI uri = new URI(url);
        httpGet.setURI(uri);
        httpGet.setHeader("Authorization", "Basic ZGVidWc6WG9vbmcxZWU=");
        HttpResponse response = httpClient.execute(httpGet);
        System.out.println(response);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
    }
}
