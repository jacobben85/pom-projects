/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.cache;

import java.util.ArrayList;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

/**
 * http://crunchify.com/how-to-create-a-simple-in-memory-cache-in-java-lightweight-cache/
 * @author jbjohn
 */
public class GenericCache<K, T> {

    private long timeToLive;
    private LRUMap crunchifyCacheMap;

    protected class GenericCacheObject {

        public long lastAccessed = System.currentTimeMillis();
        public T value;

        protected GenericCacheObject(T value) {
            this.value = value;
        }
    }

    public GenericCache(long ttl, final long timeInterval, int maxItems) {
        this.timeToLive = ttl * 1000;

        crunchifyCacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && timeInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(timeInterval * 1000);
                        } catch (InterruptedException ex) {
                            //LOGGER.error("Cache thread interuption", ex);
                        }
                        cleanup();
                    }
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    public void put(K key, T value) {
        synchronized (crunchifyCacheMap) {
            crunchifyCacheMap.put(key, new GenericCacheObject(value));
        }
    }

    public T get(K key) {
        synchronized (crunchifyCacheMap) {
            GenericCacheObject c = (GenericCacheObject) crunchifyCacheMap.get(key);

            if (c == null) {
                return null;
            } else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    public void remove(K key) {
        synchronized (crunchifyCacheMap) {
            crunchifyCacheMap.remove(key);
        }
    }

    public int size() {
        synchronized (crunchifyCacheMap) {
            return crunchifyCacheMap.size();
        }
    }

    public void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey;

        synchronized (crunchifyCacheMap) {
            MapIterator itr = crunchifyCacheMap.mapIterator();

            deleteKey = new ArrayList<K>((crunchifyCacheMap.size() / 2) + 1);
            K key;
            GenericCacheObject c;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (GenericCacheObject) itr.getValue();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (crunchifyCacheMap) {
                crunchifyCacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}
