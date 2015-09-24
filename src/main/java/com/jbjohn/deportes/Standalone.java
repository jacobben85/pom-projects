package com.jbjohn.deportes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        System.out.println(setByPath(result, "sports-content.sports-metadata.sports-content-codes.sports-content-code.[0].@code-name", "Opta 2"));
        System.out.println(searchByPath(result, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[0].@code-name"));
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

    private Object search(Object map, String key) {
        if (map instanceof HashMap) {
            HashMap<String,Object> request = (HashMap<String, Object>) map;
            return request.get(key);
        }
        if (map instanceof ArrayList) {
            ArrayList request = (ArrayList) map;
            int index = Integer.parseInt(key);
            return request.get(index);
        }
        return null;
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
