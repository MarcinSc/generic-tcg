package com.gempukku.tcg.generic.util;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.filter.DigitalObjectFilter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DigitalObjectUtils {
    public static String resolveObjectProperty(GameActionContext context, String property) {
        if (property == null)
            return null;
        int indexStart = 0;
        LinkedList<List> stack = new LinkedList<List>();

        List currentList = new LinkedList();

        int openIndex;
        int endIndex;
        openIndex = property.indexOf("*{", indexStart);
        endIndex = property.indexOf("}", indexStart);
        while (openIndex > -1 || endIndex > -1) {
            if (openIndex > -1 && openIndex < endIndex) {
                if (openIndex > indexStart)
                    currentList.add(property.substring(indexStart, openIndex));
                List newList = new LinkedList();
                currentList.add(newList);
                stack.add(currentList);
                currentList = newList;
                indexStart = openIndex + 2;
            } else {
                if (endIndex > indexStart)
                    currentList.add(property.substring(indexStart, endIndex));
                currentList = stack.removeLast();
                indexStart = endIndex + 1;
            }
            openIndex = property.indexOf("*{", indexStart);
            endIndex = property.indexOf("}", indexStart);
        }

        if (indexStart < property.length())
            currentList.add(property.substring(indexStart));

        if (stack.size() > 0)
            throw new IllegalArgumentException("Invalid property name");

        return resolveObjectProperty(context, currentList);
    }

    private static String resolveObjectProperty(GameActionContext context, List list) {
        StringBuilder result = new StringBuilder();
        for (Object o : list) {
            if (o instanceof String) {
                result.append((String) o);
            } else {
                result.append(extractProperty(context, resolveObjectProperty(context, (List) o)));
            }
        }
        return result.toString();
    }

    public static List<DigitalObject> filter(GameObjects gameObjects, DigitalObjectFilter filter, GameActionContext context, List<DigitalObject> objects) {
        List<DigitalObject> result = new ArrayList<DigitalObject>();
        for (DigitalObject object : objects) {
            if (filter == null || filter.accept(gameObjects, context, object))
                result.add(object);
        }
        return result;
    }

    private static String extractProperty(GameActionContext context, String property) {
        return context.getAttribute(property);
    }
}
