package com.jbjohn.utils.mapUtilsLibraries;

import java.util.*;

/**
 * Hash Map setter
 */
public class Setter {

    private static Object value;

    public static Object setByPath(Object map, String key, Object value) {

        key = GenericUtil.trimPath(key);
        Setter.value = value;

        if (map instanceof HashMap) {
            return Setter.setByPath((HashMap<String, Object>) map, key);
        } else {
            return Setter.setByPath((ArrayList) map, key);
        }
    }

    public static Object setByPath(Object map, String key) {

        if (map instanceof HashMap) {
            return Setter.setByPath((HashMap<String, Object>) map, key);
        } else {
            return Setter.setByPath((ArrayList) map, key);
        }
    }

    public static Object setByPath(HashMap<String, Object> map, String path) {

        List<String> stringList = GenericUtil.getKeyList(path);
        if (stringList.size() > 1) {
            String key = GenericUtil.trimKey(stringList.get(0));
            String newKey = GenericUtil.newKey(stringList);
            if (key.equals("*")) {
                HashMap<String, Object> tempMap = (HashMap<String, Object>) map.clone();
                Iterator it = tempMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    map.put((String) pair.getKey(), setByPath(map.get(pair.getKey()), newKey));
                    it.remove();
                }
            } else {
                if (map.get(key) instanceof HashMap) {
                    map.put(key, setByPath(map.get(key), newKey));
                }
                if (map.get(key) instanceof ArrayList) {
                    map.put(key, setByPath(map.get(key), newKey));
                }
            }
        } else {
            path = GenericUtil.trimKey(path);
            map.put(path, value);
        }
        return map;
    }

    public static Object setByPath(ArrayList map, String path) {

        int index = 0;
        List<String> stringList = GenericUtil.getKeyList(path);
        if (stringList.size() > 1) {
            String key = GenericUtil.trimKey(stringList.get(0));
            String newKey = GenericUtil.newKey(stringList);
            if (key.equals("*")) {
                int counter = 0;
                for (Object values : map) {
                    map.set(counter, setByPath(map.get(counter), newKey));
                    counter++;
                }
            } else {
                if (key.matches("^-?\\d+$")) {
                    index = Integer.parseInt(key);
                }
                if (map.get(index) instanceof HashMap) {
                    map.set(index, setByPath(map.get(index), newKey));
                }
                if (map.get(index) instanceof ArrayList) {
                    map.set(index, setByPath(map.get(index), newKey));
                }
            }
        } else {
            path = GenericUtil.trimKey(path);
            index = Integer.parseInt(path);
            map.set(index, value);
        }

        return map;
    }
}
