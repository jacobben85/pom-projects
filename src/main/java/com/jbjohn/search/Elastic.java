package com.jbjohn.search;

import com.google.gson.JsonObject;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Elastic search playground
 * Created by jbjohn on 8/24/15.
 */
public class Elastic {

    private static Client client;
    private String index = "movies";
    private String type = "movie";

    public Elastic(String server) {
        client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(server, 9300));
    }

    public void getResults() {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        GetResponse response = client.prepareGet(index, type, "1")
                .execute()
                .actionGet();

        String search = response.getSourceAsString();
        System.out.println(search);
    }

    public void searchResults() {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        SearchResponse response = client.prepareSearch(index)
                .setTypes("movie")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .execute()
                .actionGet();
        System.out.println(response.toString());
    }

    public void addIndex(String json) {
        String name = new Object() { }.getClass().getEnclosingMethod().getName();
        System.out.println("method : " + name);

        IndexResponse response = client.prepareIndex(index, type)
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

        IndexRequest indexRequest = new IndexRequest(index, type, "1")
                .source(json.toString());
        UpdateRequest updateRequest = new UpdateRequest(index, type, "1")
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
        DeleteResponse response = client.prepareDelete(index, type, id)
                .execute()
                .actionGet();
        System.out.println(response.toString());
    }

    public void bulkOperations() {
        JsonObject jsonObj = generateJson("The Shawshank Redemption", "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", 1994, "Crime", "Drama");
        System.out.println(jsonObj.toString());

        IndexRequestBuilder prepare1 = client.prepareIndex(index, type, "1")
                .setSource(jsonObj.toString());

        JsonObject jsonObj2 = generateJson("The Godfather", "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", 1972, "Crime", "Drama");
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(prepare1);

        IndexRequestBuilder prepare2 = client.prepareIndex(index, type, "2")
                .setSource(jsonObj2.toString());
        bulkRequest.add(prepare2);

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            System.out.println("has Failures");
        }

        Iterator<BulkItemResponse> bulkItemResponses = bulkResponse.iterator();
        while (bulkItemResponses.hasNext()) {
            BulkItemResponse bulkItemResponse = bulkItemResponses.next();
            System.out.println(bulkItemResponse.toString());
            System.out.println(bulkItemResponse.getItemId());
            System.out.println(bulkItemResponse.getOpType());
            System.out.println(bulkItemResponse.getVersion());
            System.out.println(bulkItemResponse.getResponse().toString());
        }
    }

    public JsonObject generateJson(String title, String desc, int year, String... genreVars) {

        Movie movie = new Movie();
        List<String> genres = new LinkedList<>();

        movie.setTitle(title);
        movie.setDirector(desc);
        movie.setYear(year);

        for (String genre : genreVars) {
            genres.add(genre);
        }

        movie.setGenres(genres);

        return movie.getJson();
    }

    public void shutdown() {
        client.close();
    }
}