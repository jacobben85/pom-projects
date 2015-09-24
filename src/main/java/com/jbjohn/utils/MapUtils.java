package com.jbjohn.utils;

import com.jbjohn.utils.mapUtilsLibraries.Getter;
import com.jbjohn.utils.mapUtilsLibraries.Setter;

/**
 * Utils class
 */
public class MapUtils {

    public static Object get(Object map, String key) {
        Object response = null;
        try {
            response = Getter.searchByPath(map, key);
        } catch (Exception e) {
            System.out.println(e);
        }
        return response;
    }

    public static Object set(Object map, String key, Object value) {
        Object response = null;
        try {
            response = Setter.setByPath(map, key, value);
        } catch (Exception e) {
            System.out.println(e);
        }
        return response;
    }
}
