package com.gempukku.tcg.generic.object;

import java.util.HashMap;
import java.util.Map;

public class GameObject {
    private String _identifier;
    private Map<String, String> _properties = new HashMap<String, String>();

    public String getIdentifier() {
        return _identifier;
    }

    public void setIdentifier(String identifier) {
        _identifier = identifier;
    }

    public void setProperty(String name, String value) {
        _properties.put(name, value);
    }

    public void removeProperty(String name) {
        _properties.remove(name);
    }

    public String getProperty(String name) {
        return _properties.get(name);
    }

    public Map<String, String> getAllProperties() {
        return _properties;
    }
}
