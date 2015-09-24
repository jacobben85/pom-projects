package com.jbjohn.deportes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.index.mapper.SourceToParse;

import javax.jws.Oneway;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 */
public class Standalone {

    public void process() throws IOException {

        InputStream response = this.getClass().getClassLoader().getResourceAsStream("xml/deportes/test.xml");
        StringWriter writer = new StringWriter();
        IOUtils.copy(response, writer, Charset.defaultCharset());
        String xmlString = writer.toString();
        String json = "";
        try {
            json = XmlTeamNormalize.xmlToJson(xmlString, true);
            if (false) {
                System.out.println(json);
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        Object teamKey = JsonPath.parse(json).read("$.sports-content.sports-metadata.sports-content-codes.sports-content-code[?(@.['@code-type'] == 'team')].@code-key");
        if (teamKey instanceof ArrayList) {
            int size = ((ArrayList) teamKey).size();
            int counter = 0;
            ArrayList arrayList = new ArrayList();
            while (size - counter > 0) {
                arrayList.add(((ArrayList) teamKey).get(counter));
                counter++;
            }
        }

        HashMap<String,Object> result = new ObjectMapper().readValue(json, HashMap.class);
//        System.out.println(searchByPath(result, "sports-content.sports-metadata.sports-title"));
//        System.out.println(setByPath(result, "sports-content.sports-metadata.sports-title", "New Title"));
//        System.out.println(searchByPath(result, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[0].@code-name"));
//        System.out.println(setByPath(result, "sports-content.sports-metadata.sports-content-codes.sports-content-code.[0].@code-name", "Opta 2"));
//        System.out.println(searchByPath(result, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[0].@code-name"));
        System.out.println(searchByPath(result, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[?@code-key<272].@code-type"));
//        System.out.println(JsonPath.parse(json).read("$.sports-content.sports-metadata.sports-content-codes.[*].[*]").toString());
    }


    private Object searchByPath(Object map, String path) {
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

    private Object processPredicate(Object map, String predicate) {

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

    private Object search(Object map, String key) {
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

    private Object setByPath(HashMap<String, Object> map, String path, Object value) {
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
    private Object setByPath(ArrayList map, String path, Object value) {
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
