package com.gempukku.tcg.generic.util;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.digital.DigitalObjectFilter;
import com.gempukku.tcg.generic.event.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class DigitalObjectUtils {
    public static String resolveObjectProperty(DigitalObject context, String property) {
        if (property == null)
            return null;
        int index = property.indexOf("*{");
        if (index > -1) {
            StringBuilder sb = new StringBuilder();
            sb.append(property.substring(0, index));
            int lastIndex = property.lastIndexOf("}");
            if (lastIndex < 0)
                throw new IllegalArgumentException("Invalid property value: " + property);
            sb.append(extractProperty(context, property.substring(index + 2, lastIndex)));
            sb.append(property.substring(lastIndex + 1));
            return sb.toString();
        } else
            return property;
    }

    public static List<DigitalObject> filter(GameObjects gameObjects, DigitalObjectFilter filter, DigitalObject context, List<DigitalObject> objects) {
        List<DigitalObject> result = new ArrayList<DigitalObject>();
        for (DigitalObject object : objects) {
            if (filter == null || filter.accept(gameObjects, context, object))
                result.add(object);
        }
        return result;
    }

    private static String extractProperty(DigitalObject context, String property) {
        return context.getAttributes().get(property);
    }
}
