package com.jbjohn.deportes.utils;

import java.util.HashMap;

/**
 * Created by jbjohn on 9/15/15.
 */
public class SignatureGenerator {
    private static final String CLIENT_ID = "[]";
    private static final String SECRET = "[]";

    public static String generateSignature(String url) {
        String signature = null;
        String httpVerb = "GET";
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("client_id", CLIENT_ID);
        signature = SignatureUtils.generateSignature(httpVerb, url, queryParams, null, CLIENT_ID, SECRET);
        return signature;
    }

    public static String getClientId() {
        return CLIENT_ID;
    }
}
