package com.jbjohn.staxonUtils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbjohn on 9/23/15.
 */
public enum FeedFormat {

    XML("xml") {
        @Override
        public Map<String, Object> toMap(String data) {

            try {
                String jsonData = StaxonUtils.xmlToJson(data, false);

                @SuppressWarnings("unchecked")
                Map<String, Object> dataMap = (Map<String, Object>) ObjectUtils.fromJson(jsonData);

                return dataMap;

            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    },
    JSON("json") {
        @Override
        public Map<String, Object> toMap(String data) {

            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> dataMap = (Map<String, Object>) ObjectUtils.fromJson(data);

                return dataMap;

            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    },
    CSV("csv") {
        @Override
        public Map<String, Object> toMap(String data) {

            try {
                Map<String, Object> dataMap = new HashMap<>();

                List<Map<String, Object>> itemList = new ArrayList<>();

                for (Map<String, Object> item : CsvUtils.getCsvFileIterable(new ByteArrayInputStream(data.getBytes()))) {
                    itemList.add(item);
                }

                dataMap.put(Constants.CSV_FEED_ROOT_ELEMENT_KEY, itemList);

                return dataMap;

            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
    };

    private String name;

    private FeedFormat(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Converts a raw data String in this format into Map.
     *
     * @param data the data to convert
     * @return the result of the conversion as a Map.
     * @throws IllegalArgumentException if there was a problem converting the
     *      data to the desired format.
     */
    public abstract Map<String, Object> toMap(String data);

    /**
     * @param name the feed format name.
     * @return the
     */
    public static FeedFormat forName(String name) {
        for (FeedFormat format : FeedFormat.values()) {
            if (format.getName().equals(name)) {
                return format;
            }
        }
        return null;
    }
}
