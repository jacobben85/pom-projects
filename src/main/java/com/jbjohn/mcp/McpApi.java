/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.mcp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jbjohn
 */
public class McpApi {

    public static HashMap<String, HashMap<String, String>> content = new HashMap<String, HashMap<String, String>>();
    public static HashMap<String, Integer> pathAssetsCount = new HashMap<String, Integer>();
    public static String mcpPublicKey = "EA3D6521592F4885B4B22284CF8A32C3";
    public static String mcpPrivateKey = "B9618F69B1DD4899A2E0DF3C0D2F9247";

    public static String mcpIdGetPath = System.getProperty("user.dir") + "/xml/mcpIdGet.xml";
    public static String mcpProgramsPath = System.getProperty("user.dir") + "/xml/mcpPrograms.xml";
    static long timestamp = unixtime();

    static String startDate = "Nov 1, 2014";
    static String endDate = "Dec 31, 2014";
    public static String mcpId = "";

    public static HashMap<String, String> getMcpPrograms() {
        HashMap<String, String> programs = new HashMap<String, String>();
        int numberOfIterations = 1;
        for (int i = 0; i < numberOfIterations; i++) {

            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", mcpPublicKey);
            parameters.put("ts", Long.toString(timestamp));
            parameters.put("sgn", Authenticator.generatebase64(mcpProgramsPath, mcpPrivateKey, timestamp, "", ""));
            parameters.put("page_no", String.valueOf(i + 1));
            parameters.put("page_sz", "50");
            String strXMLFilename = mcpProgramsPath;
            String xml = XMLHelper.xmlParser(strXMLFilename);
            Authenticator.signrequest(xml, timestamp);
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-type", "text/xml; charset=ISO-8859-1");
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setHttpMethod("POST");
            requestInfo.setApiKey(mcpPublicKey);
            requestInfo.setSecretKey(mcpPrivateKey);
            requestInfo.setData(xml);
            requestInfo.setIsHttps(false);
            requestInfo.setRequestPath("/api");
            requestInfo.setHost("mcm-stage.uim.univision.com");
            requestInfo.setParameters(parameters);
            requestInfo.setHeaders(headers);
            requestInfo.setSignatureType("");
            //long expirationTime = unixtime() + 1209600;
            RestApi pm = new RestApi();
            Document doc;
            try {

                doc = pm.postFeedData(requestInfo);

            } catch (IOException ex) {
                System.out.println("i has changed from:" + i);
                i = i - 1;
                System.out.println("i has changed to:" + i);
                continue;
            } catch (URISyntaxException ex) {
                System.out.println("i has changed from:" + i);
                i = i - 1;
                System.out.println("i has changed to:" + i);
                continue;
            }
            if (numberOfIterations == 1) {
                numberOfIterations = Integer.parseInt(doc
                        .getElementsByTagName("num_pages").item(0)
                        .getTextContent());
            }
            NodeList nlist = doc.getElementsByTagName("program");

            for (int x = 0; x < nlist.getLength(); x++) {
                Element e = (Element) nlist.item(x);
                String programId = e.getElementsByTagName("program_id").item(0)
                        .getTextContent();

                programs.put(e.getElementsByTagName("program_title").item(0)
                        .getTextContent(), programId);

            }
        }
        return programs;
    }

    public static long unixtime() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }

    public static void printHashMap(Map map) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove();
        }
    }

    public static void getMcpSyndicatedIds() {
        int numberOfIterations = 1;
        for (int i = 0; i < 1; i++) {
            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", mcpPublicKey);
            parameters.put("ts", Long.toString(timestamp));
            parameters.put("sgn", Authenticator.generatebase64(mcpIdGetPath, mcpPrivateKey, timestamp, startDate, endDate));
            parameters.put("page_no", String.valueOf(i + 1));
            parameters.put("page_sz", "50");
            String strXMLFilename = mcpIdGetPath;
            String xml = XMLHelper.xmlParser(strXMLFilename);
            Authenticator.signrequest(xml, timestamp);
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-type", "text/xml; charset=ISO-8859-1");
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setHttpMethod("POST");
            requestInfo.setApiKey(mcpPublicKey);
            requestInfo.setSecretKey(mcpPrivateKey);
            String xmlNew = xml.replace("$$startDate$$", startDate).replace("$$endDate$$", endDate);
            requestInfo.setData(xmlNew);
            System.out.println(xmlNew);
            requestInfo.setIsHttps(false);
            requestInfo.setRequestPath("/api");
            requestInfo.setHost("mcm-stage.uim.univision.com");
            requestInfo.setParameters(parameters);
            requestInfo.setHeaders(headers);
            requestInfo.setSignatureType("");
            RestApi pm = new RestApi();
            Document doc;
            try {
                doc = pm.postFeedData(requestInfo);
            } catch (IOException ex) {
                System.out.println("i has changed from:" + i);
                i = i - 1;
                System.out.println("i has changed to:" + i);
                continue;
            } catch (URISyntaxException ex) {
                System.out.println("i has changed from:" + i);
                i = i - 1;
                System.out.println("i has changed to:" + i);
                continue;
            }
            if (numberOfIterations == 1) {
                numberOfIterations = Integer.parseInt(doc.getElementsByTagName("num_pages").item(0).getTextContent());
            }
            NodeList nlist = doc.getElementsByTagName("item");

            for (int x = 0; x < nlist.getLength(); x++) {
                Element e = (Element) nlist.item(x);
                mcpId = e.getElementsByTagName("upload_id").item(0).getTextContent();
                if (!content.containsKey(mcpId)) {
                    NodeList childNodes = nlist.item(x).getChildNodes();
                    HashMap<String, String> childContents = new HashMap<String, String>();
                    for (int childNodesIndex = 0; childNodesIndex < childNodes.getLength(); childNodesIndex++) {
                        Node childNode = childNodes.item(childNodesIndex);
                        childContents.put(childNode.getNodeName(), childNode.getTextContent());
                    }
                    content.put(mcpId, childContents);
                }
            }
        }
    }

    public static void printContent() {
        Iterator it = content.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " => " + pair.getValue());
            it.remove();
        }
    }
}
