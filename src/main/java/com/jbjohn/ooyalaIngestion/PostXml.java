package com.jbjohn.ooyalaIngestion;

import com.jbjohn.mcp.XMLHelper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import static org.apache.http.HttpHeaders.USER_AGENT;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author jbjohn
 */
public class PostXml {

    private static String xmlPath = System.getProperty("user.dir") + "/xml/video/3012384.xml";
    private static String url = "http://qa.univision.psdops.com/feeds/video/mcp";

    public static void postToServer(String mcpId) throws IOException {
        System.out.println(mcpId);
        xmlPath = System.getProperty("user.dir") + "/xml/video/" + mcpId + ".xml";
        postToServer();
    }
    public static void postToServer() throws IOException {

        String xml = XMLHelper.xmlParser(xmlPath);

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        HttpEntity entity;

        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "application/xml");

        entity = new ByteArrayEntity(xmlParser().getBytes("UTF-8"));
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        System.out.println(result);
    }

    private static String xmlParser() {
        return xmlParser(xmlPath);
    }

    private static String xmlParser(String filepath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filepath)));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            br.close();
            return sb.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }

    }
}
