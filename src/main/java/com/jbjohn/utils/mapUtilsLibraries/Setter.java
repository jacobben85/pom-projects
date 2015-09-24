package com.jbjohn.utils.mapUtilsLibraries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 */
public class Setter {

    public static Object setByPath(HashMap<String, Object> map, String path, Object value) {
        if (path.startsWith("$.")) {
            path = path.substring(2);
        }
        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = stringList.get(0);
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.replace("[","").replace("]","");
            }
            String newKey = "";
            int counter = 0;
            for (String string : stringList) {
                counter++;
                if (counter < 2) {
                    continue;
                }
                if (newKey.length() > 1) {
                    newKey += ".";
                }
                newKey += string;
            }
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
        if (path.startsWith("$.")) {
            path = path.substring(2);
        }
        int index = 0;
        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = stringList.get(0);
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.replace("[","").replace("]","");
                index = Integer.parseInt(key);
            }
            String newKey = "";
            int counter = 0;
            for (String string : stringList) {
                counter++;
                if (counter < 2) {
                    continue;
                }
                if (newKey.length() > 1) {
                    newKey += ".";
                }
                newKey += string;
            }
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
