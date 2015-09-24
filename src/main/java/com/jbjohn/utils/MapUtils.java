package com.jbjohn.utils;

import com.jbjohn.utils.mapUtilsLibraries.Getter;
import com.jbjohn.utils.mapUtilsLibraries.Setter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 */
public class MapUtils {

    public static Object search(Object map, String key) {
        return Getter.searchByPath(map, key);
    }

    public static Object set(Object map, String key, Object value) {
        if (map instanceof HashMap) {
            return Setter.setByPath((HashMap<String, Object>) map, key, value);
        } else {
            return Setter.setByPath((ArrayList) map, key, value);
        }
    }
}
