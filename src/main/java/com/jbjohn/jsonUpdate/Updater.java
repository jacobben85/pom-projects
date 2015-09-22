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

    public static void updateJsonValue() {

        JsonParser parser = new JsonParser();
        JsonObject jsonObject = new JsonObject();

        String reader = "{'glossary':{'title':'10','GlossDiv':{'title':'S','GlossList':{'GlossEntry':{'ID':'SGML','SortAs':'SGML','GlossTerm':'Standard Generalized Markup Language','Acronym':'SGML','Abbrev':'ISO 8879:1986','GlossDef':{'para':'A meta-markup language, used to create markup languages such as DocBook.','GlossSeeAlso':['GML','XML']},'GlossSee':'markup'}}}}}";
        try {
            jsonObject = (JsonObject) parser.parse(reader);

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        System.out.println(jsonObject.toString());

        Map<String, Object> userData = null;
        try {
            userData = new ObjectMapper().readValue(jsonObject.toString(), Map.class);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        MutableJson json = new MutableJson(userData);
        json.parse("$.glossary.title", int.class);
        JSONObject jsonFinal = new JSONObject(json.map());
        System.out.println(jsonFinal.toString());
    }
}
