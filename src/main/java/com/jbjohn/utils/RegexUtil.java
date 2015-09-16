package com.jbjohn.utils;

/**
 * Regex replace test
 * Created by jbjohn on 9/16/15.
 */
public class RegexUtil {

    private static String string = "";
    private static String pattern = "";
    private static String replacement = "";

    public static void setString(String string) {
        RegexUtil.string = string;
    }

    public static void setPattern(String pattern) {
        RegexUtil.pattern = pattern;
    }

    public static void setReplacement(String replacement) {
        RegexUtil.replacement = replacement;
    }

    public String responseString() {
        String response = "";

        response = string.replaceAll(pattern, replacement);

        return response;
    }
}
