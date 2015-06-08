/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.jbjohn.csv.CharacterEncoder.MODE;

/**
 * Contains utility functions for transcoding json objects.
 */
public class JsonEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonEncoder.class);

    /**
     * Default constructor
     */
    public JsonEncoder() {
    }

    /**
     * Encodes the primitive type values for a given root JsonElement according
     * to the specified encoding mode.
     *
     * @param jsonElement - root JsonElement element
     * @param mode - encoding mode to apply
     * @return - string representation of a given jsonElement
     */
    public static String encode(JsonElement jsonElement, MODE mode) {
        if (mode == null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("No transcoding mode specified. Using default encoding, UTF-8.");
            }
            return jsonElement.toString();
        }

        return JsonEncoder.processJsonTree(jsonElement, mode);
    }

    /**
     * Processes a root JsonElement node returning its string representation, or
     * an empty string if the element is NULL.
     *
     * @param jsonElement - root JsonElement
     * @param mode	- encoding mode to apply
     * @return string - string representation of an entire json tree.
     */
    private static String processJsonTree(JsonElement jsonElement, MODE mode) {
        if (jsonElement == null) {
            return "";
        }

        JsonEncoder.processJsonElement(jsonElement, mode);
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(" jsonElement " + jsonElement);
        }

        return jsonElement.toString();
    }

    /**
     * Iterates over every sub element in a given element tree.
     *
     * @param jsonElement - jsonElement
     * @param mode - encoding mode to apply
     */
    private static void processJsonElement(JsonElement jsonElement, MODE mode) {
        if (jsonElement.isJsonObject()) {
            JsonEncoder.processJsonObj(jsonElement.getAsJsonObject(), mode);

        } else if (jsonElement.isJsonArray()) {
            JsonEncoder.processJsonArray(jsonElement.getAsJsonArray(), mode);

        } else if (jsonElement.isJsonNull()) {
            return;

        } else if (jsonElement.isJsonPrimitive()) {
            JsonEncoder.encodeJsonPrimitive(jsonElement.getAsJsonPrimitive(), mode);
        }
    }

    /**
     * Iterates over every entry for a given Json Object.
     *
     * @param jsonObj - obj to be processed
     * @param mode - encoding mode to apply
     */
    private static void processJsonObj(JsonObject jsonObj, MODE mode) {
        if (jsonObj == null) {
            return;
        }

        for (final Entry<String, JsonElement> entry : jsonObj.entrySet()) {
            JsonEncoder.processJsonElement(entry.getValue(), mode);
        }
    }

    /**
     * Iterates over every array element invoking processJsonElement on each
     * element.
     *
     * @param jsonArray - array to be processed
     * @param mode - encoding mode to apply
     */
    private static void processJsonArray(JsonArray jsonArray, MODE mode) {
        if (jsonArray == null) {
            return;
        }

        int size = jsonArray.size();

        for (int i = 0; i < size; i++) {
            JsonEncoder.processJsonElement(jsonArray.get(i), mode);
        }
    }

    /**
     * Transcodes the contents of a given JsonPrimitive from latin1 to UTF-8
     * also escapes the resulting string.
     *
     * @param jsonPrimitive - primitive to be encoded
     * @param mode	- encoding mode to apply
     */
    private static void encodeJsonPrimitive(JsonPrimitive jsonPrimitive, MODE mode) {
        jsonPrimitive = new JsonPrimitive(jsonPrimitive.toString());

    }
}
