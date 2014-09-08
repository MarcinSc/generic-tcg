package com.gempukku.tcg.generic.util;

public class StringUtils {
    public static String[] correctSplit(String string, String regex) {
        if (string.length() == 0)
            return new String[0];
        return string.split(regex);
    }
}
