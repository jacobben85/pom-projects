package com.jbjohn.utils.mapUtilsLibraries;

import java.util.*;

/**
 * Getter from HashMap
 */
public class Getter {

    private static Object search(Object map, String key) {
        String predicate = "";

        if (key.startsWith("?")) {
            predicate = key.substring(1);
        }

        if (map instanceof HashMap) {
            HashMap<String,Object> request = (HashMap<String, Object>) map;
            if (key.equals("*")) {
                return request.values();
            }
            return request.get(key);
        }
        if (map instanceof ArrayList) {
            ArrayList request = (ArrayList) map;
            if (key.equals("*")) {
                return request;
            }
            if (!predicate.equals("")) {
                return GenericUtil.processPredicate(request, predicate);
            }
            if (key.matches("^-?\\d+$")) {
                int index = Integer.parseInt(key);
                return request.get(index);
            }
            ArrayList response = new ArrayList();
            for (Object item : request) {
                Map<String, Object> itemMap = (Map<String, Object>) item;
                if (itemMap.containsKey(key)) {
                    response.add(itemMap.get(key));
                }
            }
            return response;
        }
        if (map instanceof Collection) {
            Collection<Object> request = (Collection<Object>) map;
            ArrayList<Object> response = new ArrayList<>();
            for (Object item : request) {
                response.add(item);
            }
            return response.get(0);
        }
        return map;
    }

    public static Object searchByPath(Object map, String path) {

        path = GenericUtil.trimPath(path);

        List<String> stringList = GenericUtil.getKeyList(path);
        if (stringList.size() > 1) {
            String key = stringList.get(0);
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.replace("[","").replace("]","");
            }
            map = search(map, key);
            String newKey = GenericUtil.newKey(stringList);
            path = newKey;
        } else {
            if (path.startsWith("[") && path.endsWith("]")) {
                path = path.replace("[","").replace("]","");
            }
            return search(map, path);
        }
        return searchByPath(map, path);
    }
}
