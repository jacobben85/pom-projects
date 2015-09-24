package com.jbjohn.deportes;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.io.IOUtils;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;

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
        System.out.println(teamKey.toString());
        if (teamKey instanceof ArrayList) {
            int size = ((ArrayList) teamKey).size();
            int counter = 0;
            ArrayList arrayList = new ArrayList();
            while (size - counter > 0) {
                arrayList.add(((ArrayList) teamKey).get(counter));
                counter++;
            }
            System.out.println(arrayList.toString());
        }
//        json = JsonPath.parse(json).set("$.sports-content.sports-metadata.sports-content-codes.sports-content-code[?(@['@code-type'] == 'team')].@code-key", 10).jsonString();
//        System.out.println(json);
    }
}
