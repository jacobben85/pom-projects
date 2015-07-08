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
            public String load(String key) {
                return createRandom();
            }
        }
        );
    }

    public Object getEntry(String key) {
        return cache.getUnchecked(key);
    }

    public void setEntry(String key, String value) {
        cache.put(key, value);
    }

    private String createRandom() {
        return "I'm a random string or resource... Be creative ;)";
    }
}
