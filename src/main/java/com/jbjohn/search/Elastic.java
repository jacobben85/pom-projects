package com.jbjohn.search;

import com.google.gson.JsonObject;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.concurrent.ExecutionException;

/**
 * Elastic search playground
 * Created by jbjohn on 8/24/15.
 */
public class Elastic {

    private static Client client;

    public Elastic(String server) {
        client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(server, 9300));
    }

    public void getResults() {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        GetResponse response = client.prepareGet("movies", "movie", "1")
                .execute()
                .actionGet();

        String search = response.getSourceAsString();
        System.out.println(search);
    }

    public void searchResults() {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        SearchResponse response = client.prepareSearch("movies")
                .setTypes("movie")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .execute()
                .actionGet();
        System.out.println(response.toString());
    }

    public void addIndex(String json) {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        IndexResponse response = client.prepareIndex("movies", "movie")
                .setSource(json)
                .execute()
                .actionGet();

        String index = response.getIndex();
        System.out.println(index);
        String id = response.getId();
        System.out.println(id);
    }

    public void updateIndex(JsonObject json) {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        IndexRequest indexRequest = new IndexRequest("movies", "movie", "1")
                .source(json.toString());
        UpdateRequest updateRequest = new UpdateRequest("movies", "movie", "1")
                .doc(json.toString())
                .upsert(indexRequest);
        try {
            UpdateResponse response = client.update(updateRequest).get();
            System.out.println(response.getId());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.toString());
        }
    }

    public void doDelete(String id) {
        DeleteResponse response = client.prepareDelete("movies", "movie", id)
                .execute()
                .actionGet();
        System.out.println(response.toString());
    }

    public void shutdown() {
        client.close();
    }
}