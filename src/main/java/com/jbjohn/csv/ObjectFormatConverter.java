/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jbjohn.csv.CharacterEncoder.MODE;

/**
 * Contains utility functions relevant to object conversion.
 */
public class ObjectFormatConverter {

    /**
     * Local Gson JSON serializer instance
     */
    protected Gson jsonSerializer;

    /**
     * Logger instance
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectFormatConverter.class);

    /**
     * Default Constructor.
     */
    public ObjectFormatConverter() {

        this.initializeJsonSerializer();
    }

    /**
     * Converts a given object instance to a JSON formatted string
     * representation. NOTE: Objects should be kept light (i.e. encapsulated
     * beans) so as to reduce conversion complexity.
     *
     * @param instance - object instance to convert to a JSON formatted string
     * @param mode - CharacterEncoder.MODE to apply (if null, no transcoding is
     * applied)
     * @return string - json string representation of Object instance
     *
     */
    public String objectToJson(Object instance, MODE mode) {
        try {
            return EnhancedString.replaceSpanishCharacters(JsonEncoder.encode(this.jsonSerializer.toJsonTree(instance), mode).replaceAll("¿", "&iquest;"));

        } catch (Exception e) {
            LOGGER.error("Error converting json string to object: " + e.getMessage());
        }
        return null;
    }

    /**
     * Initializes the Json serializer used for parsing.
     */
    private void initializeJsonSerializer() {
        this.jsonSerializer = new GsonBuilder().setDateFormat("MMM d, yyyy H:mm:ss a") // May 2, 2011 11:45:00 PM
                .serializeNulls()
                .create();
    }

}
