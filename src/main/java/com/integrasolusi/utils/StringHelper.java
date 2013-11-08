package com.integrasolusi.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 * Programmer   : pancara
 * Date         : 6/11/11
 * Time         : 10:56 AM
 */
public class StringHelper {
    private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();

    public static String generateRandomText() {
        return generateRandomText(5);
    }

    public static String generateRandomText(int length) {
        char[] dictionary = "1234567890abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder buffer = new StringBuilder();
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(dictionary.length);
            buffer.append(dictionary[index]);
        }
        return StringUtils.upperCase(buffer.toString());
    }

    public static String addNoiseToEmail(String email, char noiseChar) {
        String[] tokens = StringUtils.split(email, "@");
        int noiseCount = tokens[0].length() / 3;
        if (noiseCount < 3) noiseCount = 3;

        String s = StringUtils.substring(tokens[0], 0, tokens[0].length() - noiseCount + 1);

        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < noiseCount; i++)
            sb.append(noiseChar);
        if (tokens.length > 1) sb.append(tokens[1]);
        return sb.toString();
    }

    public static String addNoiseToEmail(String email) {
        return addNoiseToEmail(email, '_');
    }

    public static String removeHtmlTag(String text) {
        StringBuilder buffer = new StringBuilder();
        if (StringUtils.isEmpty(text))
            return buffer.toString();
        char[] chars = text.toCharArray();

        boolean appendToBuffer = true;
        for (char c : chars) {
            if (c == '<') {
                appendToBuffer = false;
                continue;
            }

            if (c == '>') {
                appendToBuffer = true;
                continue;
            }
            if (appendToBuffer) buffer.append(c);
        }
        return buffer.toString();
    }

    public static String stringToHex(String str, boolean upperCase) {
        if (str == null)
            return null;
        
        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        if (upperCase) {
            return StringUtils.upperCase(hex.toString());
        } else {
            return hex.toString();
        }
    }

    public static String stringToHex(String str) {
        return stringToHex(str, true);
    }

    public static String hexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }

        return sb.toString();
    }
}
