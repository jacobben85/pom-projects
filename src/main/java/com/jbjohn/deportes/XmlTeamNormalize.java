package com.jbjohn.deportes;

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

        InputStream xsl = this.getClass().getClassLoader().getResourceAsStream("xsl/deportes/empty.xsl");
        InputStream xml = this.getClass().getClassLoader().getResourceAsStream("xml/deportes/xmlTeam.xml");
        StreamSource stylesource = new StreamSource(xsl);
        TransformerFactory factory = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer;

        try {

            StreamSource source = new StreamSource(xml);
            transformer = factory.newTransformer(stylesource);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);

            System.out.println(writer.toString());
            return true;

        } catch (TransformerConfigurationException ex) {
        } catch (TransformerException ex) {
        }

        return false;
    }
}
