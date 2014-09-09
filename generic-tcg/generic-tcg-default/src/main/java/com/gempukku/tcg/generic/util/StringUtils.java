package com.gempukku.tcg.generic.util;

import java.util.Collection;

public class StringUtils {
    public static String[] correctSplit(String string, String regex) {
        if (string.length() == 0)
            return new String[0];
        return string.split(regex);
    }

    public static String join(Collection<String> string, String separator) {
        return org.apache.commons.lang.StringUtils.join(string, separator);
    }
}
