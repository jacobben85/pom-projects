package com.jbjohn.utils.mapUtilsLibraries;

import java.util.*;

/**
 */
public class Getter {

    private static Object processPredicate(Object map, String predicate) {

        int operation = 0;
        String predicateKey = "";
        String predicateValue = "";

        if (predicate.contains("==")) {
            operation = 1;
            List<String> predicateList = Arrays.asList(predicate.split("=="));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        } else if (predicate.contains(">")) {
            operation = 2;
            List<String> predicateList = Arrays.asList(predicate.split(">"));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        } else if (predicate.contains("<")) {
            operation = 3;
            List<String> predicateList = Arrays.asList(predicate.split("<"));
            predicateKey = predicateList.get(0);
            predicateValue = predicateList.get(1);
        }

        if (map instanceof ArrayList) {
            ArrayList request = (ArrayList) map;
            ArrayList response = new ArrayList();

            for (Object item : request) {
                Map<String, Object> itemMap = (Map<String, Object>) item;
                if (itemMap.containsKey(predicateKey)) {

                    int valueInt = 0;
                    int predicateInt = 0;
                    if (((String) itemMap.get(predicateKey)).matches("^-?\\d+$") && predicateValue.matches("^-?\\d+$")) {
                        valueInt = Integer.parseInt((String) itemMap.get(predicateKey));
                        predicateInt = Integer.parseInt(predicateValue);
                    }

                    switch (operation) {
                        case 1:
                            if (itemMap.get(predicateKey).equals(predicateValue)) {
                                response.add(itemMap);
                            }
                            break;
                        case 2:
                            if (valueInt > predicateInt) {
                                response.add(itemMap);
                            }
                            break;
                        case 3:
                            if (valueInt < predicateInt) {
                                response.add(itemMap);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

            return response;
        }

        return map;
    }

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
                return processPredicate(request, predicate);
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
        if (path.startsWith("$.")) {
            path = path.substring(2);
        }
        List<String> stringList = Arrays.asList(path.split("\\."));
        if (stringList.size() > 1) {
            String key = stringList.get(0);
            if (key.startsWith("[") && key.endsWith("]")) {
                key = key.replace("[","").replace("]","");
            }
            map = search(map, key);
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
