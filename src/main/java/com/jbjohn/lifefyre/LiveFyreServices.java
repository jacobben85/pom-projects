/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.lifefyre;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.livefyre.Livefyre;
import com.livefyre.core.Collection;
import com.livefyre.core.Network;
import com.livefyre.core.Site;
import com.livefyre.exceptions.ApiException;
import com.livefyre.exceptions.LivefyreException;
import com.livefyre.model.CollectionData;
import com.livefyre.type.CollectionType;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author jbjohn
 */
public class LiveFyreServices {

    private String networkName = "univision-int-0";
    private String networkId = "univision-int-0.fyre.co";
    private String networkKey = "KguZvm1Qo8LB8h6qDBDk7uj+vpM=";

    private String siteId = "304807";
    private String siteKey = "5yLotAPq5DAbIhRFtbHg471D1XI=";

    private Network network;
    private Site site;
    private String systemToken;
    private Collection collection;
    private Boolean useSSL = true;

    public LiveFyreServices() {
        network = getNetwork(networkId, networkKey);
        network.setSsl(useSSL);
        systemToken = getLFToken();
        site = getSite(siteId, siteKey);

//        System.out.println(network);
//        System.out.println(systemToken);
//        System.out.println(site);
    }

    public void setSite(String siteId, String siteKey) {
        this.siteId = siteId;
        this.siteKey = siteKey;

        site = getSite(siteId, siteKey);
    }

    private Network getNetwork(String networkId, String networkKey) {
        return Livefyre.getNetwork(networkId, networkKey);
    }

    private Site getSite(String siteId, String siteKey) {
        return network.getSite(siteId, siteKey);
    }

    private String getLFToken() {
        return network.buildLivefyreToken();
    }

    public Collection getCollection(String title, String id, String url) {
        CollectionData collectionData = new CollectionData(CollectionType.COMMENTS, title, id, url);
        return new Collection(site, collectionData);
    }

    public String getCollectionId(Collection collection) {
        String collectionId;

        try {
            JsonObject data = collection.getCollectionContent();
            System.out.println(data);
            collectionId = data.get("collectionSettings").getAsJsonObject().get("collectionId").getAsString();
        } catch (Exception e) {
            collectionId = "";
            System.out.println(e);
        }
        return collectionId;
    }

    public String getToken() {
        if (!network.validateLivefyreToken(systemToken)) {
            systemToken = getLFToken();
        }
        return systemToken;
    }

    private String collectionUrl(Collection collection) {

        String collectionUrn;
        String url;
        String siteUrn = "";
        collection.createOrUpdate();
        String collectionId = getCollectionId(collection);
        System.out.println("collection id" + collectionId);

        try {
            siteUrn = site.getUrn();
            collectionUrn = collection.getUrn();
        } catch (ApiException ex) {
            collectionUrn = "Exception : " + ex.toString();
        } catch (LivefyreException ex) {
            collectionUrn = "Exception : " + ex.toString();
        }

        System.out.println(collectionUrn);
        System.out.println(collectionId);
        System.out.println(siteUrn);

        if (!collectionUrn.substring(0, 9).equals("Exception")) {
            url = this.networkName + ".admin.fyre.co/api/v3.0/" +
                    collectionUrn;
        } else if (!collectionId.equals("")) {
            url = this.networkName + ".admin.fyre.co/api/v3.0/" +
                    siteUrn + ":collection=" + collectionId;
        } else {
            url = "invalid";
        }

        return (useSSL) ? ("https://" + url) : ("http://" + url);
    }

    public JsonObject buildJsonRequest(String collectionId, String title) {

        List<String> authorList = new ArrayList<String>();
        authorList.add("https://facebook.com/univision");
        authorList.add("https://facebook.com/jacobben85");
        JsonObject query = new JsonObject();
        JsonObject twitter = new JsonObject();
        JsonObject twitterQuery = new JsonObject();
        JsonArray tags = new JsonArray();
        JsonArray authors = new JsonArray();
        JsonObject stream = new JsonObject();
        JsonObject data = new JsonObject();

        tags.add(new JsonPrimitive("#uvideos"));
        tags.add(new JsonPrimitive("#univision"));
        twitterQuery.add("queries", tags);
        for (String author : authorList) {
            JsonObject authorQuery = new JsonObject();
            authorQuery.add("url", new JsonPrimitive(author));
            authors.add(authorQuery);
        }
        twitter.add("twitter", twitterQuery.getAsJsonObject());
        twitter.add("users", authors);
        query.add("select", twitter.getAsJsonObject());
        stream.add("query", query.getAsJsonObject());

        data.addProperty("name", title);
        data.addProperty("collectionId", collectionId);
        data.addProperty("enabled", true);
        data.addProperty("premoderated", true);
        data.addProperty("source", 2);
        data.add("stream", stream.getAsJsonObject());

        return data;
    }

    public void updateCollectionRule() {
        Collection collectiontmp = getCollection("Article testing new", "test-article-id-new", "http://hackathon.univision.com/");

        //System.out.println(collectiontmp);
        collectiontmp.createOrUpdate();
        //System.out.println(collectiontmp.toString());
        String url = collectionUrl(collectiontmp);

        System.out.println("Url : " + url);
//        String collectionId = getCollectionId(collectiontmp);
//        System.out.println("collection id : " + collectionId);

//        JsonObject rule = this.buildJsonRequest(collectionId, "Combination Rule - Latest");
//        System.out.println(rule);
//        JsonObject jsonResponse = createCollectionRule(url, rule);
//        System.out.println(jsonResponse);
//        String ruleId = jsonResponse.get("data").getAsJsonObject().get("id").getAsString();
//        System.out.println(ruleId);
//        JsonObject deleteResponse = deleteRuleById(collection, ruleId);
//        System.out.println(deleteResponse);
    }

    private JsonObject createCollectionRule(String url, JsonObject data) {

        String urlWithToken = url + ":collectionRules/" + "?lftoken=" + getToken();

        System.out.println("Update");
        System.out.println(urlWithToken);

        JsonObject jsonResponse = null;
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost(urlWithToken);
            StringEntity params = new StringEntity(data.toString());
            request.addHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            String result = EntityUtils.toString(response.getEntity());

            System.out.println(result);

            JsonParser parser = new JsonParser();
            jsonResponse = (JsonObject) parser.parse(result);

        } catch (IOException ex) {
            // testing
        } catch (ParseException ex) {
            // tesitng
        } catch (JsonSyntaxException ex) {
            // testing
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return jsonResponse;
    }

    private JsonObject deleteRuleById(Collection collection, String ruleId) {
        JsonObject jsonResponse = null;

        String url = collectionUrl(collection) + ":rule=" + ruleId +
                ":collectionRules?lftoken=" + getToken();

        System.out.println("Delete");
        System.out.println(url);

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpDelete request = new HttpDelete(url);
            HttpResponse response = httpClient.execute(request);

            String result = EntityUtils.toString(response.getEntity());

            System.out.println(result);

            JsonParser parser = new JsonParser();
            jsonResponse = (JsonObject) parser.parse(result);

            System.out.println(jsonResponse.toString());

        } catch (IOException ex) {
            // test
        }

        return jsonResponse;
    }

    public JsonObject getExistingRules(Collection collection) throws URISyntaxException {

        JsonObject jsonResponse = null;
        String url = collectionUrl(collection);
        if (url.equals("")) {
            return jsonResponse;
        }
        url += ":collectionRules?lftoken=" + getLFToken();

        System.out.println(url);

        jsonResponse = getHttpResponse(1, new URI(url));

        if (jsonResponse != null) {
            System.out.println(jsonResponse.toString());
        }

        return jsonResponse;
    }

    private JsonObject getHttpResponse(int type, URI url) {

        JsonObject jsonResponse = null;
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpRequestBase request;

        switch (type) {
            case 1:
                request = new HttpGet(url);
                break;

            case 2:
                request = new HttpPost(url);
                break;

            case 3:
                request = new HttpDelete(url);
                break;

            default:
                request = new HttpGet(url);
                break;
        }
        try {
            HttpResponse response = httpClient.execute(request);
            String result = EntityUtils.toString(response.getEntity());
            JsonParser parser = new JsonParser();
            jsonResponse = (JsonObject) parser.parse(result);
        } catch (IOException ex) {
            // testing
        } catch (ParseException ex) {
            // testing
        } catch (JsonSyntaxException ex) {
            // testing
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return jsonResponse;
    }

    public void saveExistingRules(JsonObject collectionRules) {

        JsonArray rulesArray = collectionRules.get("data").getAsJsonObject().get("data").getAsJsonArray();

        for (JsonElement rule : rulesArray) {
            JsonObject ruleObj = rule.getAsJsonObject();

            //System.out.println(ruleObj.get("source").getAsString());
            //System.out.println(ruleObj.get("id").getAsString());
            //System.out.println(ruleObj.get("enabled").getAsBoolean());
            System.out.println(ruleObj.get("name").getAsString());
            //System.out.println(ruleObj.toString());
            //System.out.println(ruleObj.get("stream").getAsJsonObject().get("query").getAsJsonObject().toString());

            if (ruleObj.get("source").getAsString().equals("2")) { // twitter
                // {"exclude":{"twitter":{"mentionedUsers":[{"number":491704052,"id":"uvideos","display":"UVideos"}],"queries":["#nokey","nokey"]}},"require":{"twitter":{"isDirectReply":0,"retweetPolicy":0}},"select":{"twitter":{"trackReplies":true,"mentionedUsers":[{"number":40924038,"id":"univision","display":"Univision"}],"queries":["#uvideos"," #univision","uvideos","univision"]}}}
                JsonObject query = ruleObj.get("stream").getAsJsonObject().get("query").getAsJsonObject();
                JsonObject select = query.get("select").getAsJsonObject();
                System.out.println(select.toString());

                System.out.println("Include");
                if (select.get("twitter").getAsJsonObject().get("queries") != null) {
                    JsonArray queries = select.get("twitter").getAsJsonObject().get("queries").getAsJsonArray();
                    System.out.println(queries.toString());
                    for (JsonElement tag : queries) {
                        System.out.println(tag.getAsString());
                    }
                }

                if (query.get("exclude") != null) {
                    System.out.println("Exclude");
                    JsonObject exclude = query.get("exclude").getAsJsonObject();
                    System.out.println(exclude.toString());
                    if (exclude.get("twitter").getAsJsonObject().get("queries") != null) {
                        JsonArray queriesExclude = exclude.get("twitter").getAsJsonObject().get("queries").getAsJsonArray();
                        System.out.println(queriesExclude.toString());
                        for (JsonElement tag : queriesExclude) {
                            System.out.println(tag.getAsString());
                        }
                    }
                }
            } else if (ruleObj.get("source").getAsString().equals("6")) { // rss
                // {"select":{"feed":{"url":"http://feedsyn.univision.com/lo-ultimo-de-univision"}}}
                String rssFeedUrl = ruleObj
                        .get("stream").getAsJsonObject()
                        .get("query").getAsJsonObject()
                        .get("select").getAsJsonObject()
                        .get("feed").getAsJsonObject()
                        .get("url").getAsString();
                System.out.println(rssFeedUrl);
            } else if (ruleObj.get("source").getAsString().equals("10")) { // facebook page
                // {"select":{"facebookFanpage":{"fanpageId":"259955926518","fanpageUsername":"univision"}}}
                JsonObject query = ruleObj.get("stream").getAsJsonObject().get("query").getAsJsonObject();
                String fanPage = query.get("select").getAsJsonObject().get("facebookFanpage").getAsJsonObject().get("fanpageUsername").toString();
                System.out.println(query.toString());
                System.out.println(fanPage);
            } else if (ruleObj.get("source").getAsString().equals("12")) { // facebook feed
                // {"select":{"facebookFeed":{"terms":["#univision","univision"]}}}
                JsonObject query = ruleObj.get("stream").getAsJsonObject().get("query").getAsJsonObject();
                System.out.println(query.toString());

                JsonObject select = query.get("select").getAsJsonObject();
                if (select.get("facebookFeed").getAsJsonObject().get("terms") != null) {
                    JsonArray queries = select.get("facebookFeed").getAsJsonObject().get("terms").getAsJsonArray();
                    System.out.println(queries.toString());
                    for (JsonElement tag : queries) {
                        System.out.println(tag.getAsString());
                    }
                }

                if (query.get("exclude") != null) {
                    JsonObject exclude = query.get("exclude").getAsJsonObject();
                    System.out.println(exclude.toString());
                    if (exclude.get("facebookFeed").getAsJsonObject().get("terms") != null) {
                        JsonArray queriesExclude = exclude.get("facebookFeed").getAsJsonObject().get("terms").getAsJsonArray();
                        System.out.println(queriesExclude.toString());
                        for (JsonElement tag : queriesExclude) {
                            System.out.println(tag.getAsString());
                        }
                    }
                }
            } else if (ruleObj.get("source").getAsString().equals("11")) { // instagram
                // {"select":{"instagram":{"tags":["univision"]}}}
                JsonObject query = ruleObj.get("stream").getAsJsonObject().get("query").getAsJsonObject();
                JsonObject select = query.get("select").getAsJsonObject();
                System.out.println(select.toString());
                if (select.get("instagram").getAsJsonObject().get("tags") != null) {
                    JsonArray queries = select.get("instagram").getAsJsonObject().get("tags").getAsJsonArray();
                    System.out.println(queries.toString());
                    for (JsonElement tag : queries) {
                        System.out.println(tag.getAsString());
                    }
                }

                if (query.get("exclude") != null) {
                    JsonObject exclude = query.get("exclude").getAsJsonObject();
                    System.out.println(exclude.toString());
                    if (exclude.get("instagram").getAsJsonObject().get("tags") != null) {
                        JsonArray queriesExclude = exclude.get("instagram").getAsJsonObject().get("tags").getAsJsonArray();
                        System.out.println(queriesExclude.toString());
                        for (JsonElement tag : queriesExclude) {
                            System.out.println(tag.getAsString());
                        }
                    }
                }
            }

        }
    }
}
