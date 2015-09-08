package com.jbjohn.staxonUtils;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

import javax.xml.stream.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

/**
 * Collection of utility methods for converting XML to JSON, and JSON to XML.
 */
public final class StaxonUtils {

    private StaxonUtils() {

    }

    /**
     * XML to JSON to converter according to the
     * <a href="https://github.com/beckchr/staxon/wiki/Converting-XML-to-JSON">
     * Staxon API</a>.
     */
    public static String xmlToJson(String xml, boolean formatted) throws XMLStreamException {

        InputStream input = new ByteArrayInputStream(xml.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        JsonXMLConfig config = new JsonXMLConfigBuilder()
                //input params
                .multiplePI(true)
                        //output params
                .autoArray(true)
                .autoPrimitive(true)
                .multiplePI(true)
                .prettyPrint(formatted)
                .namespaceSeparator(':')
                .namespaceDeclarations(true)
                .build();

        try {

            XMLInputFactory xmlInputFactory = new JsonXMLInputFactory(config);

            /* Create reader (XML). */
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(input);

            /* Create writer (JSON). */
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);

            /* Copy events from reader to writer. */
            writer.add(reader);

            /* Close reader/writer. */
            reader.close();
            writer.close();

            try {
                return output.toString("UTF-8");

            } catch (UnsupportedEncodingException e) {
                throw new XMLStreamException(e.getMessage());
            }

        } catch (Exception e) {
            // do nothing
        }
        return "";
    }

    /**
     * JSON to XML converter according to the
     * <a href="https://github.com/beckchr/staxon/wiki/Converting-JSON-to-XML">
     * Staxon API</a>.
     */
    public static String jsonToXml(String json, boolean formatted) throws XMLStreamException {

        InputStream input = new ByteArrayInputStream(json.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();
        try {
            /* Create reader (JSON). */
            XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);

            /* Create writer (XML). */
            XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
            if (formatted) {
                writer = new PrettyXMLEventWriter(writer); // format output
            }

            /* Copy events from reader to writer. */
            writer.add(reader);

            /* Close reader/writer. */
            reader.close();
            writer.close();

            try {
                return output.toString("UTF-8");

            } catch (UnsupportedEncodingException e) {
                throw new XMLStreamException(e.getMessage());
            }

        } catch (Exception e) {
            // do nothing
        }
        return "";
    }

    /**
     * XML to JSON to converter according to the
     * <a href="https://github.com/beckchr/staxon/wiki/Converting-XML-to-JSON">
     * Staxon API</a>. Suppresses any exceptions.
     */
    public static String xmlToJsonOrNull(String xml, boolean formatted) {
        try {
            return xmlToJson(xml, formatted);
        } catch (XMLStreamException | FactoryConfigurationError e) {
            return null;
        }
    }

    /**
     * JSON to XML converter according to the
     * <a href="https://github.com/beckchr/staxon/wiki/Converting-JSON-to-XML">
     * Staxon API</a>. Suppresses any exceptions.
     */
    public static String jsonToXmlOrNull(String json, boolean formatted) {
        try {
            return jsonToXml(json, formatted);
        } catch (XMLStreamException | FactoryConfigurationError e) {
            return null;
        }
    }

    public static final int XML_DECLARATION_SIZE = 38;

    private static final int XML_ELEMENT_SIZE_OVERHEAD = 5;
    private static final int XML_EMPTY_ELEMENT_SIZE_OVERHEAD = 4;
    private static final int XML_ATTRIBUTE_SIZE_OVERHEAD = 3;

    /**
     * Calculates the size in bytes of that resulting XML. It does not include
     * the size of the XML declaration but it can be added manually using the
     * constant value {@link #XML_DECLARATION_SIZE}.
     *
     * @param jsonMap map compliant with being transformed into XML.
     * @return the size of the resulting XML.
     */
    public static Integer getXmlLengthForJsonMap(Map<String, Object> jsonMap) {
        return getXmlLengthForJsonObject(null, jsonMap);
    }

    private static Integer getXmlLengthForJsonObject(String previousKey, Object object) {

        if (object instanceof Map) {

            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) object;

            int length = 0;

            boolean hasElements = false;

            for (Map.Entry<String, Object> entry : map.entrySet()) {

                String key = entry.getKey();
                Object value = entry.getValue();

                if (key.startsWith("@")) {

                    if (value != null) {
                        length += (key.length() + value.toString().length() + XML_ATTRIBUTE_SIZE_OVERHEAD);
                    }

                } else {
                    Iterable<?> items;

                    if (value instanceof Iterable) {
                        items = (Iterable<?>) value;

                    } else {
                        items = Collections.singleton(value);
                    }

                    for (Object item : items) {
                        hasElements = true;

                        if (item != null) {
                            length += (key.length() * 2 + XML_ELEMENT_SIZE_OVERHEAD);
                            length += getXmlLengthForJsonObject(key, item);

                        } else {
                            length += (key.length() + XML_EMPTY_ELEMENT_SIZE_OVERHEAD);
                        }
                    }
                }
            }

            if (!hasElements && previousKey != null) {
                length -= (previousKey.length() + 1);
            }

            return length;

        } else {
            if (object != null) {
                return object.toString().length();
            } else {
                return 0;
            }
        }
    }
}