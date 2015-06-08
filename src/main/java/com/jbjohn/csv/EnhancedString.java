/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.util.StringTokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnhancedString {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnhancedString.class);

    public static String toHexString(String value) {
        StringBuilder result = new StringBuilder();

        try {

            if (value != null) {
                // get the hexadecimal representation of 'value'
                for (int index = 0; index < value.length(); ++index) {
                    result.append(Integer.toHexString(value.charAt(index)));
                }

            }
        } catch (Exception e) {
            LOGGER.error("Exception occured while trying to convert int Hex  for value " + value + " -- " + e);
        }
        return result.toString();
    }

    public static String toUnhexedString(String value) {

        String hexString = new String(value);
        int length = 0;
        StringBuffer result = new StringBuffer();
        try {
            // number of hex characters
            if (hexString != null) {
                if (hexString.length() % 2 != 0) {
                    hexString = "0" + hexString;
                }

                // get the length of 'hexString' ONCE
                length = hexString.length();
            }

            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("hexString " + hexString);
            }
            result = new StringBuffer(length >> 1);

            for (int index = 0; index < length; index += 2) {
                result.append((char) Integer.parseInt(hexString.substring(index,
                        index + 2), 16));
            }
        } catch (Exception ex) {
            //logger.error("Exception occured while trying toUnhex  for value "+value + " -- "+ ex);
        }
        return result.toString();
    }

    /**
     * replacing spanish characters with their HTML equivalents
     * @param inputString
     * @return
     */
    public static String replaceSpanishCharacters(String inputString) {
        String outputString = null;
        if (inputString != null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("inputString " + inputString);
            }
            outputString = inputString.replaceAll("Á", "&Aacute;").replaceAll("á", "&aacute;").replaceAll("É", "&Eacute;").replaceAll("é", "&eacute;").replaceAll("Í", "&Iacute;").replaceAll("í", "&iacute;").replaceAll("Ö", "&Ouml;").replaceAll("ö", "&ouml;");
            outputString = outputString.replaceAll("Ó", "&Oacute;").replaceAll("ó", "&oacute;").replaceAll("Ñ", "&Ntilde;").replaceAll("ñ", "&ntilde;").replaceAll("Ú", "&Uacute;").replaceAll("ú", "&uacute;").replaceAll("Ü", "&Uuml;").replaceAll("ü", "&uuml;");
            outputString = outputString.replaceAll("À", "&Agrave;").replaceAll("È", "&Egrave;").replaceAll("Ì", "&Igrave;").replaceAll("Ò", "&Ograve;").replaceAll("Ù", "&Ugrave;").replaceAll("à", "&agrave;").replaceAll("è", "&egrave;").replaceAll("ì", "&igrave;").replaceAll("ò", "&ograve;").replaceAll("ù", "&ugrave;");
            outputString = outputString.replaceAll("Ã", "&Atilde;").replaceAll("ã", "&atilde;").replaceAll("Õ", "&Otilde;").replaceAll("õ", "&otilde;").replaceAll("Ä", "&Auml;").replaceAll("ä", "&auml;").replaceAll("Ë", "&Euml;").replaceAll("ë", "&euml;").replaceAll("Ï", "&Iuml;").replaceAll("ï", "&iuml;");
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("outputString " + outputString);
        }
        return outputString;

    }

    /**
     * replacing Spanish characters with their HTML equivalents
     * @param inputString
     * @return
     */
    public static String replaceHTMLEuivalents(String inputString) {
        String outputString = null;
        if (inputString != null) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("inputString " + inputString);
            }
            outputString = inputString.replaceAll("&Aacute;", "Á").replaceAll("&aacute;", "á").replaceAll("&Eacute;", "É").replaceAll("&eacute;", "é").replaceAll("&Iacute;", "Í").replaceAll("&iacute;", "í").replaceAll("&Ouml;", "Ö").replaceAll("&ouml;", "ö");
            outputString = outputString.replaceAll("&Oacute;", "Ó").replaceAll("&oacute;", "ó").replaceAll("&Ntilde;", "Ñ").replaceAll("&ntilde;", "ñ").replaceAll("&Uacute;", "Ú").replaceAll("&uacute;", "ú").replaceAll("&Uuml;", "Ü").replaceAll("&uuml;", "ü");
            outputString = outputString.replaceAll("&Agrave;", "À").replaceAll("&Egrave;", "È").replaceAll("&Igrave;", "Ì").replaceAll("&Ograve;", "Ò").replaceAll("&Ugrave;", "Ù").replaceAll("&agrave;", "à").replaceAll("&egrave;", "è").replaceAll("&igrave;", "ì").replaceAll("&ograve;", "ò").replaceAll("&ugrave;", "ù");
            outputString = outputString.replaceAll("&Atilde;", "Ã").replaceAll("&atilde;", "ã").replaceAll("&Otilde;", "Õ").replaceAll("&otilde;", "õ").replaceAll("&Auml;", "Ä").replaceAll("&auml;", "ä").replaceAll("&Euml;", "Ë").replaceAll("&euml;", "ë").replaceAll("&Iuml;", "Ï").replaceAll("&iuml;", "ï");
        }

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("outputString " + outputString);
        }
        return outputString;

    }

    /**
     * static method for extracting values from long Strings separated by
     * delimiters and "="
     *
     * @param value
     * @param attrType
     * @param delimiter
     * @return
     */
    public static String getAttrVal(String value, String attrType, String delimiter) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("value " + value);
        }
        if (!(value == null || value.equals(""))) {

            StringTokenizer adTagValues = new StringTokenizer(value,
                    delimiter);

            while (adTagValues.hasMoreTokens()) {
                String temp = adTagValues.nextToken();

                String arr[] = temp.split("=");
                if (arr != null && arr[0].equalsIgnoreCase(attrType)) {

                    if (arr.length > 1) {
                        if (LOGGER.isInfoEnabled()) {
                            LOGGER.info("arrt val " + arr[1]);
                        }
                        return arr[1];

                    }
                }
            }
        }
        return null;
    }

    public static void main(String ar[]) {
        System.out.println("result " + EnhancedString.toUnhexedString("49503d31302e3130342e302e3134337c49523d6e6f6e65"));
    }

}
