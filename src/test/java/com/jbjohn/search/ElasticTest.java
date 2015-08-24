package com.jbjohn.search;

import com.google.gson.JsonObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jbjohn on 8/24/15.
 */
public class ElasticTest {

    private static Elastic elastic;
    private String json;
    private JsonObject jsonOBJ;

    @BeforeClass
    public static void setUpClass() {
        elastic = new Elastic("local.cms.uvn.io");
    }

    @AfterClass
    public static void tearDownClass() {
        elastic.shutdown();
    }

    @Before
    public void setUp() {
        json = generateJson();
        jsonOBJ = generateJson(true);
    }

    public String generateJson() {
        return generateJson(false).toString();
    }

    public JsonObject generateJson(Boolean object) {
        Movie movie = new Movie();
        List<String> genres = new LinkedList<>();
        movie.setTitle("The Godfather");
        movie.setDirector("Francis Ford Coppola");
        movie.setYear(1972);

        genres.add("Drama");
        genres.add("Crime");

        movie.setGenres(genres);

        return movie.getJson();
    }

    public void testGetResults() {
        elastic.getResults();
    }

    public void testSearchResults() {
        elastic.searchResults();
    }

    public void testMovie() {
        System.out.println(json);
    }

    public void testAddIndex() {
        elastic.addIndex(json);
    }

    @Test
    public void testUpdateIndex() {
        elastic.updateIndex(jsonOBJ);
    }

    public void testDoDelete() {
        elastic.doDelete("2");
        elastic.doDelete("3");
        elastic.doDelete("4");
        elastic.doDelete("5");
        elastic.doDelete("6");
    }
}
