/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.ooyalaIngestion;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author jbjohn
 */
public class ClosedCaptions {

    /**
     * ClosedCaptions
     */
    public void fetchClosedCaptions() {

        Map<String, Object> closedCaptions = new HashMap<>();
        closedCaptions.put("es", "http://vmsmedia.s3.amazonaws.com/captionupl/97F/9E4/97F9E4AA418C44CA8AC6DC9DD8C035C4.dfxp");
        //closedCaptions.put("es", "http://vmsmedia.s3.amazonaws.com/iupl/69E/4D8/69E4D8C6F62D413B9E66026655C08DE3.xml");
        //closedCaptions.put("en", "http://vmsmedia.s3.amazonaws.com/captionupl/E3E/82F/E3E82F96B0B2441188B3E118717990C9_dfxp.xml");
        System.out.println(closedCaptions.toString());
        Document testDoc = mergeClosedCaptions(closedCaptions);
        String test = "";
        try {
            test = getStringFromDocument(testDoc);
        } catch (TransformerException ex) {
            System.out.println(ex.toString());
        }
        System.out.println(test.replace("xml_lang", "xml:lang"));
    }

    /**
     * Closed caption file received from MCP is not directly supported by
     * Ooyala, hence they need to be reprocessed.
     * @param closedCaptions
     * @return
     */
    private Document mergeClosedCaptions(Map closedCaptions) {

        Document docFinal = null;

        Iterator entries = closedCaptions.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry closedCaption = (Map.Entry) entries.next();

            try {
                InputStream dxfpXsl = this.getClass().getClassLoader().getResourceAsStream("xsl/dxfp_es.xsl");
                System.out.println(dxfpXsl.toString());

                if (closedCaption.getKey().toString().equals("en")) {
                    dxfpXsl = this.getClass().getClassLoader().getResourceAsStream("xsl/dxfp_en.xsl");
                }

                Document tempDoc;
                StreamSource source = new StreamSource(closedCaption.getValue().toString());
                StreamSource stylesource = new StreamSource(dxfpXsl);

                TransformerFactory factory = TransformerFactory.newInstance();
                javax.xml.transform.Transformer transformer = factory.newTransformer(stylesource);

                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);

                DocumentBuilderFactory xmlfactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = xmlfactory.newDocumentBuilder();

                tempDoc = docBuilder.parse(new InputSource(new StringReader(writer.toString())));
                if (docFinal == null) {
                    docFinal = tempDoc;
                } else {
                    Element divNode = (Element) tempDoc.getElementsByTagName("div").item(0);
                    Node tempImp = docFinal.importNode(divNode, true);
                    Node ttNode = docFinal.getElementsByTagName("body").item(0);
                    ttNode.appendChild(tempImp);
                }
            } catch (TransformerException | ParserConfigurationException | SAXException | IOException e) {
                System.out.println(e.toString());
            }
        }

        return docFinal;
    }

    public static String getStringFromDocument(Document doc) throws TransformerException {
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        javax.xml.transform.Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.transform(domSource, result);
        return writer.toString();
    }
}
