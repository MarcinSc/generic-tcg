package com.gempukku.tcg.generic.util;

import com.gempukku.tcg.digital.DigitalObject;

public class GameObjectUtils {
    public static String resolveObjectProperty(DigitalObject gameObject, String property) {
        if (property == null)
            return null;
        int index = property.indexOf("*{");
        if (index > -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(property.substring(0, index));
            int lastIndex = property.lastIndexOf("}");
            if (lastIndex < 0)
                throw new IllegalArgumentException("Invalid property value: " + property);
            sb.append(extractProperty(gameObject, property.substring(index + 2, lastIndex)));
            sb.append(property.substring(lastIndex + 1));
            return sb.toString();
        } else
            return property;
    }

    private static String extractProperty(DigitalObject gameObject, String property) {
        return gameObject.getAttributes().get(property);
    }
}
