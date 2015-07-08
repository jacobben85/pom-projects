package com.jbjohn.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class Cache {

    private static final long MAX_SIZE = 100;

    private final LoadingCache<String, Object> cache;

    public Cache() {
        cache = CacheBuilder.newBuilder().maximumSize(MAX_SIZE).build(new CacheLoader<String, Object>() {
            @Override
            public Object load(String key) {
                return generateData(key);
            }
        });
    }

    public Object getEntry(String key) {
        return cache.getUnchecked(key);
    }

    public void setEntry(String key, String value) {
        cache.put(key, value);
    }

    private Object generateData(String key) {

        switch (key) {
            case "accessRules":
                break;
            case "syndicationRules":
                break;
            default:
                break;
        }
        return "testing";
    }
}
