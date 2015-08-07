package com.jbjohn.deportes;

import java.io.InputStream;
import java.io.StringWriter;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jbjohn
 */
public class XmlFormat {

    public boolean process() {
        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            InputStream xml = this.getClass().getClassLoader().getResourceAsStream("xml/deportes/xmlTeam.xml");
            StreamSource source = new StreamSource(xml);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            System.out.println(writer.toString());
        } catch (TransformerConfigurationException ex) {
        } catch (TransformerException ex) {
        }
        return true;
    }
}
