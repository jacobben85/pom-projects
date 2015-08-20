package com.jbjohn.deportes;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
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

        InputStream xml1 = this.getClass().getClassLoader().getResourceAsStream("xml/deportes/standings.xml");

        InputStream xsl1 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/bbc-to-xts.xsl");
        String response1 = transformer(xml1, xsl1, false);

        InputStream xml2 = new ByteArrayInputStream(response1.getBytes());
        InputStream xsl2 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/xts-to-2.2.xsl");
        String response2 = transformer(xml2, xsl2, false);

        System.out.println("Last XSL processing ");
        InputStream xml3 = new ByteArrayInputStream(response2.getBytes());
        InputStream xsl3 = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/normalize.xsl");
        String response3 = transformer(xml3, xsl3, true);

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
}
