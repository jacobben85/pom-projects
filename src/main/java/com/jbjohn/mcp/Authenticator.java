package com.jbjohn.mcp;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;
import java.util.Vector;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jbjohn
 */
public class Authenticator {

    public static void getSignature(String type, RequestInfo request) {
        if ("SHA-1".equals(type)) {
            request.setSignature(generateSignatureSHA1(request));
        } else if ("SHA-256".equals(type)) {
            request.setSignature(generateSignatureSHA256(request));
        } else {
            request.setSignature("");
        }
    }

    public static String generatebase64(String path, String privateKey, long timeStamp, String startDate, String endDate) {
        try {
            String message = XMLHelper.xmlParser(path);
            message = message.replace("$$startDate$$", startDate);
            message = message.replace("$$endDate$$", endDate);
            message = signrequest(message, timeStamp);
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(privateKey.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            String hash = Base64.encodeBase64String(sha256HMAC.doFinal(message.getBytes()));
            return hash;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error");
            return "Error generating signature";
        } catch (InvalidKeyException e) {
            System.out.println("Error");
            return "Error generating signature";
        } catch (IllegalStateException e) {
            System.out.println("Error");
            return "Error generating signature";
        }
    }

    public static String signrequest(String payload, long timeStamp) {
        String s = Objects.toString(timeStamp, null);
        payload = payload + s;
        return payload;
    }

    private static String sha1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }

        return sb.toString();
    }

    private static String generateSignatureSHA1(RequestInfo request) {
        //method + clientid + url + secretkey
        String signature = request.getTextToSign();
        try {
            signature = sha1(signature);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        return signature;
    }

    private static String generateSignatureSHA256(RequestInfo request) {
        try {
            String stringToSign = request.getTextToSign();
            stringToSign += concatenateParams(request.getParameters(), "");
            stringToSign += request.getData() != null ? request.getData() : "";
            MessageDigest digestProvider;
            digestProvider = MessageDigest.getInstance("SHA-256");
            digestProvider.reset();
            byte[] digest = digestProvider.digest(stringToSign.getBytes());
            String encodedBytes = new Base64().encodeToString(digest);
            return encodedBytes.substring(0, 43);

        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    private static String concatenateParams(HashMap<String, String> parameters, String separator) {
        Vector<String> keys = new Vector<String>(parameters.keySet());
        Collections.sort(keys);
        String string = "";
        for (Enumeration<String> e = keys.elements(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            String value = (String) parameters.get(key);
            if (!string.isEmpty()) {
                string += separator;
            }
            string += key + "=" + value;
        }
        return string;
    }
}
