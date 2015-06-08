/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jbjohn.csv;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/**
 * Contains utility functions for handling character encodings.
 */
public class CharacterEncoder {

    /**
     * Supported encoding modes
     */
    public static enum MODE {
        ENCODE_TO_LATIN1, ENCODE_TO_UTF8
    }

    /**
     * Encodes an input string to either latin1 (from UTF-8) or vice-versa.
     *
     * @param input - string to be encoded.
     * @param mode - encoding routing to apply.
     * @return string
     */
    public static String encode(String input, MODE mode) {
        switch (mode) {
            case ENCODE_TO_LATIN1:
                return CharacterEncoder.utf8ToLatin1(input);

            case ENCODE_TO_UTF8:
                return CharacterEncoder.latin1ToUtf8(input);

            default:
                return "";
        }
    }

    /**
     * Converts a UTF-8 string to a latin1 equivalent.
     *
     * @param String input - UTF-8 encoded string
     * @return String output - latin1 encoded output
     */
    private static String utf8ToLatin1(String input) {
        // if( logger.isInfoEnabled() ) logger.info("Converting UTF-8 to latin1");
        Charset utf8 = Charset.forName("UTF-8");
        Charset latin1 = Charset.forName("ISO-8859-1");

        // decode as UTF-8
        CharBuffer data = utf8.decode(ByteBuffer.wrap(input.getBytes()));

        // encode to ISO-8559-1
        ByteBuffer outputBuffer = latin1.encode(data);
        byte[] outputBytes = outputBuffer.array();

        String output = new String(outputBytes, latin1);

        return output;
    }

    /**
     * Converts a latin1 string to its UTF-8 equivalent.
     *
     * @param String input - latin1 encoded input
     * @return String output - UTF-8 encoded output
     */
    private static String latin1ToUtf8(String input) {
        // if( logger.isInfoEnabled() ) logger.info("Converting latin1 to UTF-8");
        Charset latin1 = Charset.forName("ISO-8859-1");
        Charset utf8 = Charset.forName("UTF-8");

        // decode as ISO-8559-1
        CharBuffer data = latin1.decode(ByteBuffer.wrap(input.getBytes()));

        // encode to UTF-8
        ByteBuffer outputBuffer = utf8.encode(data);
        byte[] outputBytes = outputBuffer.array();

        String output = new String(outputBytes, utf8);

        return output;
    }
}
