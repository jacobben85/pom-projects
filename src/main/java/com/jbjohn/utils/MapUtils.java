package com.jbjohn.utils;

import com.jbjohn.utils.mapUtilsLibraries.Getter;
import com.jbjohn.utils.mapUtilsLibraries.Setter;

/**
 * Utils class
 */
public class MapUtils {

    public static Object get(Object map, String key) {
        return Getter.searchByPath(map, key);
    }

    public static Object set(Object map, String key, Object value) {
        return Setter.setByPath(map, key, value);
    }
}
