package deportes;

import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author jbjohn
 */
public class TranformableXmlTest {

    public TranformableXmlTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of parseXmlDocument method, of class TranformableXml.
     */
    @Test
    public void testParseXmlDocument() {
        System.out.println("testParseXmlDocument");
        TranformableXml instance = new TranformableXml();
        instance.setRawXml(getResource("xml/deportes/event-stats.xml"));
        instance.setXslt(getResource("xsl/deportes/empty.xsl"));

        instance.process();
        String processedXml = instance.getProcessedXml();
        System.out.println(processedXml);
    }

    public String getResource(String filePath) {
        InputStream xml = this.getClass().getClassLoader().getResourceAsStream(filePath);
        StreamSource source = new StreamSource(xml);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            System.out.println("Exception " + ex.toString());
        }
        return writer.toString();
    }
}
