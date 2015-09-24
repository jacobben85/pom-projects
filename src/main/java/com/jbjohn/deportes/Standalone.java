package com.jbjohn.deportes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbjohn.utils.MapUtils;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Standalone sandbox for validation
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

        HashMap<String,Object> result = new ObjectMapper().readValue(json, HashMap.class);
        ArrayList titles = new ArrayList();
        titles.add("title 1");
        titles.add("title 2");
        System.out.println(MapUtils.get(result, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[?@code-type=team].@code-key"));
    }
}
