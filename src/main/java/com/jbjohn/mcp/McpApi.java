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
import org.w3c.dom.NodeList;

/**
 *
 * @author jbjohn
 */
public class McpApi {

    public static HashMap<String, HashMap<String, String>> content = new HashMap<String, HashMap<String, String>>();
    public static HashMap<String, Integer> pathAssetsCount = new HashMap<String, Integer>();
    public static String mcppublickey = "EA3D6521592F4885B4B22284CF8A32C3";
    public static String mcpprivatekey = "B9618F69B1DD4899A2E0DF3C0D2F9247";

    public static String mcpProgramsPath = System.getProperty("user.dir") + "/xml/mcpPrograms.xml";
    static long timestamp = unixtime();

    public static HashMap<String, String> getMcpPrograms() {
        HashMap<String, String> programs = new HashMap<String, String>();
        int numberOfIterations = 1;
        for (int i = 0; i < numberOfIterations; i++) {

            HashMap<String, String> parameters = new HashMap<String, String>();
            parameters.put("id", mcppublickey);
            parameters.put("ts", Long.toString(timestamp));
            parameters.put("sgn", Authenticator.generatebase64(mcpProgramsPath, mcpprivatekey, timestamp, "", ""));
            parameters.put("page_no", String.valueOf(i + 1));
            parameters.put("page_sz", "50");
            String strXMLFilename = mcpProgramsPath;
            String xml = XMLHelper.xmlParser(strXMLFilename);
            Authenticator.signrequest(xml, timestamp);
            HashMap<String, String> headers = new HashMap<String, String>();
            headers.put("Content-type", "text/xml; charset=ISO-8859-1");
            RequestInfo requestInfo = new RequestInfo();
            requestInfo.setHttpMethod("POST");
            requestInfo.setApiKey(mcppublickey);
            requestInfo.setSecretKey(mcpprivatekey);
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
}
