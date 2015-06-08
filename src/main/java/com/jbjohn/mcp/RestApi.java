package com.jbjohn.mcp;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;

public class RestApi {

    public String getFeedData(RequestInfo requestInfo) {
        java.net.URI uri;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet httpget = null;
        URIBuilder builder = new URIBuilder();
        builder.setScheme((requestInfo.getIsHttps() ? "https" : "http"));
        builder.setHost(requestInfo.getHost()).setPath(requestInfo.getRequestPath());
        for (String key : requestInfo.getParameters().keySet()) {
            builder.setParameter(key, requestInfo.getParameters().get(key));
        }

        if (requestInfo.getSignature() != null && !requestInfo.getSignature().isEmpty()) {
            builder.setParameter("signature", requestInfo.getSignature());
        }

        try {
            uri = builder.build();
            httpget = new HttpGet(uri);
            for (String key : requestInfo.getHeaders().keySet()) {
                httpget.setHeader(key, requestInfo.getHeaders().get(key));
            }
        } catch (URISyntaxException e) {
            System.out.println(e.toString());
        }

        HttpResponse response;
        String result = "";
        try {
            response = client.execute(httpget);
            result = EntityUtils.toString(response.getEntity(), "iso-8859-1");
        } catch (ClientProtocolException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public Document postFeedData(RequestInfo requestInfo) throws IOException, URISyntaxException {
        java.net.URI uri;
        Document doc;
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30000).setSocketTimeout(30000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();

        URIBuilder builder = new URIBuilder();
        builder.setScheme((requestInfo.getIsHttps() ? "https" : "http"));
        builder.setHost(requestInfo.getHost()).setPath(requestInfo.getRequestPath());

        for (String key : requestInfo.getParameters().keySet()) {
            builder.setParameter(key, requestInfo.getParameters().get(key));
        }

        if (requestInfo.getSignature() != null && !requestInfo.getSignature().isEmpty()) {
            builder.setParameter("signature", requestInfo.getSignature());
        }

        HttpPost post;
        uri = builder.build();
        post = new HttpPost(uri);
        post.setHeader("Content-type", "text/xml; charset=ISO-8859-1");
        HttpEntity entity;

        entity = new ByteArrayEntity(requestInfo.getData().getBytes("UTF-8"));
        post.setEntity(entity);

        HttpResponse response;
        String result;

        response = client.execute(post);
        result = EntityUtils.toString(response.getEntity());
        doc = XMLHelper.xmlFromString(result);
        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter("d:\\TEST.txt"));
        writer.write(result);
        writer.close();
        return doc;
    }

}
