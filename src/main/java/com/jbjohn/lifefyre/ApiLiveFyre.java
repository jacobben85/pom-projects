/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.lifefyre;

import com.google.gson.JsonArray;
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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author jbjohn
 */
public class ApiLiveFyre {

    private String networkName = "univision-int-0";
    private String networkId = "univision-int-0.fyre.co";
    private String networkKey = "KguZvm1Qo8LB8h6qDBDk7uj+vpM=";

    private String siteId = "304812";
    private String SiteKey = "6+OmNRwyIwN1hczONjbj3k8AXgo=";

    private Map<String, Object> LiveFyreApiToken(Map<String, Object> results) {

        Network network = Livefyre.getNetwork(this.networkId, this.networkKey);

        network.setSsl(false);

        String systemToken = network.buildLivefyreToken();

        //make sure a system token is still valid
        //Boolean isValid = network.validateLivefyreToken(systemToken);

        //get the network URN
        //String networkUrn = network.getUrn();

        //get the urn for a specific user
        //String userUrn = network.getUrnForUser("guest");
        //Ping for Pull (set url then sync user afterwards)
        //network.setUserSyncUrl("www.example-qa.com/user/{id}");
        //network.syncUser("guest");
        //get a Site class
        Site site = network.getSite(this.siteId, this.SiteKey);
        //update a Site's data
        //site.getData().setId("101");
        //site.getData().setKey("examplesite101base64key");
        //get the site's urn
        String siteUrn = site.getUrn();
        
        CollectionData collectionData = new CollectionData(CollectionType.COMMENTS, "Article testing", "test-article-id", "http://hackathon.univision.com/");
        Collection collection = new Collection(site, collectionData);
        
        String collectionUrn = "";
        JsonObject data = null;
        
        try {
            data = collection.getCollectionContent();
            collection.createOrUpdate();
            collectionUrn = collection.getUrn();
        } catch (ApiException ex) {
            collectionUrn = "Exception : " + ex.toString();
        } catch (LivefyreException ex) {
            collectionUrn = "Exception : " + ex.toString();
        }
        
        String collectionId = "";
        try {
            collectionId = data.get("collectionSettings").getAsJsonObject().get("collectionId").getAsString();
            results.put("collectionId", collectionId);
        } catch (Exception e) {
            
        }

        String url = "";

        if (!collectionUrn.substring(0, 9).equals("Exception")) {
            url = "https://" + this.networkName + ".admin.fyre.co/api/v3.0/" + 
                  collectionUrn + ":collectionRules/?lftoken=" + systemToken;
        } else if (!collectionId.equals("")) {
            url = "https://" + this.networkName + ".admin.fyre.co/api/v3.0/" + 
                    siteUrn + ":collection=" + collectionId + 
                    ":collectionRules/?lftoken=" + systemToken;
        }
        
        results.put("url", url);
        
        return results;
        
    }
    
    private Map<String, Object> postDataToUrl(Map<String, Object> results) {
        
        String url = results.get("url").toString();
        
        if (url.equals("")) {
            results.put("exception", "Invalid Url");
            return results;
        }
        
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead 

        try {
            JsonObject data = this.buildJsonRequest(results);
            HttpPost request = new HttpPost(url);
            StringEntity params =new StringEntity(data.toString());
            request.addHeader("Content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            
            String result = EntityUtils.toString(response.getEntity());

            //results.put("data", data.toString());
            
            JsonParser parser = new JsonParser();
            JsonObject jsonResponse = (JsonObject) parser.parse(result);
            results.put("response", jsonResponse.toString());
            
            if (jsonResponse.get("code").getAsString().equals("200") || jsonResponse.get("code").getAsString().equals("201")) {
                results.put("ruleId", jsonResponse.get("data").getAsJsonObject().get("id").getAsString());
            }
            results.put("responseCode", jsonResponse.get("code").getAsString());
            
            // handle response here...
        }catch (IOException ex) {
            // handle exception here
            results.put("exception", ex.toString());
        } catch (ParseException ex) {
            // handle exception here
            results.put("exception", ex.toString());
        } catch (JsonSyntaxException ex) {
            // handle exception here
            results.put("exception", ex.toString());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        
        return results;
    }
    
    private JsonObject buildJsonRequest(Map<String, Object> results) {
        
        JsonObject query = new JsonObject();
        JsonObject twitter = new JsonObject();
        JsonObject twitterQuery = new JsonObject();
        JsonArray tags = new JsonArray();
        JsonObject stream = new JsonObject();
        JsonObject data = new JsonObject();
        
        tags.add(new JsonPrimitive("#uvideos"));
        tags.add(new JsonPrimitive("#univision"));
        twitterQuery.add("queries", tags);
        twitter.add("twitter", twitterQuery.getAsJsonObject());
        query.add("select", twitter.getAsJsonObject());
        stream.add("query", query.getAsJsonObject());
        
        data.addProperty("name", "Combination Rule - Latest");
        data.addProperty("collectionId", results.get("collectionId").toString());
        data.addProperty("enabled", true);
        data.addProperty("premoderated", true);
        data.addProperty("source", 2);
        data.add("stream", stream.getAsJsonObject());
        
        return data;
    }
    
    private void deleteRuleById(String ruleId) {
        String url = "https://univision-int-0.admin.fyre.co/api/v3.0/urn:livefyre:univision-int-0.fyre.co:site=304812:collection=74699051:rule=" +
                ruleId + ":collectionRules/?lftoken=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkb21haW4iOiJ1bml2aXNpb24taW50LTAuZnlyZS5jbyIsInVzZXJfaWQiOiJzeXN0ZW0iLCJkaXNwbGF5X25hbWUiOiJzeXN0ZW0iLCJleHBpcmVzIjoxNDMwMzU2OTA2fQ.u2TK6YGgPypu0BCLt_VBqgzN5yw-reEUhMCg4bGFYq4";
        
        HttpClient httpClient = HttpClientBuilder.create().build();
        
        try {
            HttpDelete request = new HttpDelete(url);
            HttpResponse response = httpClient.execute(request);  
            
            String result = EntityUtils.toString(response.getEntity());

            //results.put("data", data.toString());
            
            JsonParser parser = new JsonParser();
            JsonObject jsonResponse = (JsonObject) parser.parse(result);
            
            System.out.println(jsonResponse.toString());
            
        } catch (IOException ex) {
            
        }
    }
    
    public Map<String, Object> getResponseObject(HttpServletRequest request) {
        Map<String, Object> results = new HashMap<String, Object>();
        Map<String, Object> allResults = new HashMap<String, Object>();

        results = this.LiveFyreApiToken(results);
        
        //allResults = this.postDataToUrl(results);
        
        //results.putAll(allResults);
        
        //this.deleteRuleById(results.get("ruleId").toString());

        return results;
    }

}
