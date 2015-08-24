package com.jbjohn.search;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.List;

/**
 * Created by jbjohn on 8/24/15.
 */
public class Movie {
    private String title;
    private String director;
    private int year;
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public JsonObject getJson() {
        JsonObject data = new JsonObject();
        JsonArray genreValues = new JsonArray();

        data.addProperty("title", title);
        data.addProperty("director", director);
        data.addProperty("year", year);

        for(String genre : genres) {
            genreValues.add(new JsonPrimitive(genre));
        }

        data.add("genres", genreValues);

        return data;
    }
}
