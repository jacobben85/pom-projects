package com.jbjohn.jsonUpdate;

import java.io.IOException;
import java.util.Map;

import com.amazonaws.util.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Json Updater
 */
public class Updater {

    public static void updateJsonValue(String jsonString) {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = new JsonObject();

        String reader = jsonString;
        try {
            jsonObject = (JsonObject) parser.parse(reader);

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        Map<String, Object> userData = null;
        try {
            userData = new ObjectMapper().readValue(jsonObject.toString(), Map.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MutableJson json = new MutableJson(userData);
        json.parse("$.sports-content.sports-metadata.sports-title", Integer.class);
        JSONObject jsonFinal = new JSONObject(json.map());
        System.out.println(jsonFinal.toString());
    }
}
