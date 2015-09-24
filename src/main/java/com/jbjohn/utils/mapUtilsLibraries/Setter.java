package com.jbjohn.utils.mapUtilsLibraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = GenericUtil.trimKey(stringList.get(0));
            String newKey = GenericUtil.newKey(stringList);
            if (map.get(key) instanceof HashMap) {
                map.put(key, setByPath(map.get(key), newKey));
            }
            if (map.get(key) instanceof ArrayList) {
                map.put(key, setByPath(map.get(key), newKey));
            }
        } else {
            if (path.startsWith("[") && path.endsWith("]")) {
                path = path.replace("[","").replace("]","");
            }
            map.put(path, value);
        }
        return map;
    }

    public static Object setByPath(ArrayList map, String path) {

        int index = 0;
        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = GenericUtil.trimKey(stringList.get(0));
            if (key.matches("^-?\\d+$")) {
                index = Integer.parseInt(key);
            }
            String newKey = GenericUtil.newKey(stringList);
            if (map.get(index) instanceof HashMap) {
                map.set(index, setByPath(map.get(index), newKey));
            }
            if (map.get(index) instanceof ArrayList) {
                map.set(index, setByPath(map.get(index), newKey));
            }
        } else {
            if (path.startsWith("[") && path.endsWith("]")) {
                path = path.replace("[","").replace("]","");
                index = Integer.parseInt(path);
            }
            map.set(index, value);
        }
        return map;
    }
}
