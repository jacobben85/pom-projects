package deportes;

import com.amazonaws.util.StringInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author jbjohn
 */
public class TranformableXml {

    private String rawXml;
    private String processedXml;
    private String xslt;
    private boolean compress = false;
    private boolean encode = false;
    private boolean transformed = false;

    public String getRawXml() {
        return rawXml;
    }

    public void setRawXml(String rawXml) {
        this.rawXml = rawXml;
    }

    public String getProcessedXml() {
        return processedXml;
    }

    public void setProcessedXml(String processedXml) {
        this.processedXml = processedXml;
    }

    public String getXslt() {
        return xslt;
    }

    public void setXslt(String xslt) {
        this.xslt = xslt;
    }

    public boolean isCompress() {
        return compress;
    }

    public void setCompress(boolean compress) {
        this.compress = compress;
    }

    public boolean isEncode() {
        return encode;
    }

    public void setEncode(boolean encode) {
        this.encode = encode;
    }

    public boolean isTransformed() {
        return transformed;
    }

    public void setTransformed(boolean transformed) {
        this.transformed = transformed;
    }

    public Document parseXmlDocument() {
        Document doc = null;
        DocumentBuilder builder;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            builder = factory.newDocumentBuilder();
            try {
                doc = builder.parse(new StringInputStream(processedXml));
            } catch (SAXException | IOException ex) {
                System.out.println("Exception" + ex.toString());
            }
        } catch (ParserConfigurationException ex) {
            System.out.println("Exception" + ex.toString());
        }
        return doc;
    }

    public void process() {
        if (xslt != null) {
            StringReader stylereader = new StringReader(xslt);
            StreamSource stylesource = new StreamSource(stylereader);
            TransformerFactory factory = TransformerFactory.newInstance();
            javax.xml.transform.Transformer transformer;
            try {
                StringReader reader = new StringReader(rawXml);
                StreamSource source = new StreamSource(reader);
                transformer = factory.newTransformer(stylesource);
                StringWriter writer = new StringWriter();
                StreamResult result = new StreamResult(writer);
                transformer.transform(source, result);

                processedXml = writer.toString();
                transformed = true;
            } catch (TransformerConfigurationException ex) {
                System.out.println("Exception" + ex.toString());
            } catch (TransformerException ex) {
                System.out.println("Exception" + ex.toString());
            }
        }
    }

    private <T> T basicGetValue(String xpathExpression, Object doc, T defaultValue, QName type) {

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();
        XPathExpression expr;
        try {
            expr = xpath.compile(xpathExpression);
        } catch (XPathExpressionException e) {
            return defaultValue;
        }

        try {
            return (T) expr.evaluate(doc, type);
        } catch (XPathExpressionException e) {
            return defaultValue;
        }
    }

    /**
     * Utility
     * @param node
     * @param attributeName
     * @return
     */
    public String getAttribute(Node node, String attributeName) {
        try {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute != null) {
                return attribute.getNodeValue();
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex.toString());
        }
        return null;
    }

    /**
     * Utility
     * @param node
     * @param attributeName
     * @param defaultValue
     * @return
     */
    public String getAttribute(Node node, String attributeName, String defaultValue) {
        try {
            Node attribute = node.getAttributes().getNamedItem(attributeName);
            if (attribute == null) {
                return defaultValue;
            } else {
                return attribute.getNodeValue();
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Utility
     * @param xpathExpression
     * @param doc
     * @return
     */
    public Node getNode(String xpathExpression, Object doc) {
        return this.basicGetValue(xpathExpression, doc, null, XPathConstants.NODE);
    }

    /**
     * Utility
     * @param xpathExpression
     * @param doc
     * @param defaultValue
     * @return
     */
    public NodeList getListValue(String xpathExpression, Object doc, NodeList defaultValue) {
        return this.basicGetValue(xpathExpression, doc, defaultValue, XPathConstants.NODESET);
    }

    /**
     * Utility
     * @param xpathExpression
     * @param doc
     * @param defaultValue
     * @return
     */
    public String getStringValue(String xpathExpression, Object doc, String defaultValue) {
        return this.basicGetValue(xpathExpression, doc, defaultValue, XPathConstants.STRING);
    }
}
