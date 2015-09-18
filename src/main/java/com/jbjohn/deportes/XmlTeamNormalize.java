package com.jbjohn.deportes;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

import java.io.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author jbjohn
 */
public class XmlTeamNormalize {

    public boolean process() {

        InputStream xml1 = this.getClass().getClassLoader().getResourceAsStream("xml/deportes/event-stats-809979.xml");

        InputStream xsl1 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/bbc-to-xts.xsl");
        String response1 = transformer(xml1, xsl1, false);

        InputStream xml2 = new ByteArrayInputStream(response1.getBytes());
        InputStream xsl2 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/xts-to-2.2.xsl");
        String response2 = transformer(xml2, xsl2, false);

        System.out.println("Last XSL processing ");
        InputStream xml3 = new ByteArrayInputStream(response2.getBytes());
        InputStream xsl3 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/normalize.xsl");
        String response3 = transformer(xml3, xsl3, false);

        InputStream xml4 = new ByteArrayInputStream(response3.getBytes());
        InputStream xsl4 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/processingInstructions.xsl");
        String response4 = transformer(xml4, xsl4, true);

        try {
            String json = xmlToJson(response4, true);
            if (true) {
                System.out.println(json);
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        return !response1.isEmpty() && !response2.isEmpty() && !response3.isEmpty();
    }

    private String transformer(InputStream xml, InputStream xsl, boolean print) {
        StreamSource stylesource = new StreamSource(xsl);
        TransformerFactory factory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer;

        try {

            StreamSource source = new StreamSource(xml);
            transformer = factory.newTransformer(stylesource);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String response = writer.toString();
            if (print) {
                System.out.println(response);
            }
            return response;

        } catch (TransformerConfigurationException ex) {
        } catch (TransformerException ex) {
        }
        return "";
    }

    public static String xmlToJson(String xml, boolean formatted) throws XMLStreamException {
        InputStream input = new ByteArrayInputStream(xml.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        JsonXMLConfig config = new JsonXMLConfigBuilder()
                .autoArray(true)
                .autoPrimitive(true)
                .prettyPrint(formatted)
                .build();

        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
            try {
                return output.toString("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new XMLStreamException(e.getMessage());
            }
        } finally {
            // dp nothing
        }
    }
}
