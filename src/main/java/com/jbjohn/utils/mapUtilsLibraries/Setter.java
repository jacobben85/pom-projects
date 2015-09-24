package com.jbjohn.utils.mapUtilsLibraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Hash Map setter
 */
public class Setter {

    public static Object setByPath(Object map, String key, Object value) {

        key = GenericUtil.trimPath(key);

        if (map instanceof HashMap) {
            return Setter.setByPath((HashMap<String, Object>) map, key, value);
        } else {
            return Setter.setByPath((ArrayList) map, key, value);
        }
    }

    public static Object setByPath(HashMap<String, Object> map, String path, Object value) {

        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = stringList.get(0);
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.replace("[","").replace("]","");
            }

            String newKey = GenericUtil.newKey(stringList);

            if (map.get(key) instanceof HashMap) {
                map.put(key, setByPath((HashMap<String, Object>) map.get(key), newKey, value));
            }
            if (map.get(key) instanceof ArrayList) {
                map.put(key, setByPath((ArrayList) map.get(key), newKey, value));
            }
        } else {
            if (path.startsWith("[") && path.endsWith("]")) {
                path = path.replace("[","").replace("]","");
            }
            map.put(path, value);
        }
        return map;
    }

    public static Object setByPath(ArrayList map, String path, Object value) {

        int index = 0;
        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = stringList.get(0);
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.replace("[","").replace("]","");
                index = Integer.parseInt(key);
            }

            String newKey = GenericUtil.newKey(stringList);

            if (map.get(index) instanceof HashMap) {
                map.set(index, setByPath((HashMap<String, Object>) map.get(index), newKey, value));
            }
            if (map.get(index) instanceof ArrayList) {
                map.set(index, setByPath((ArrayList) map.get(index), newKey, value));
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
