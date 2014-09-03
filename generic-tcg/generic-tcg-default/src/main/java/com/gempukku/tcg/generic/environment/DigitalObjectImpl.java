package com.gempukku.tcg.generic.environment;

import com.gempukku.tcg.digital.DigitalObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DigitalObjectImpl implements DigitalObject {

    private String _id;
    private Map<String, String> _attributes;

    public DigitalObjectImpl(String id, Map<String, String> attributes) {
        _id = id;
        _attributes = new HashMap<String, String>(attributes);
    }

    public String getId() {
        return _id;
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(_attributes);
    }

    protected void updateDigitalObject(String newId, Map<String, String> attributeDiff) {
        _id = newId;
        for (Map.Entry<String, String> attributeValue : attributeDiff.entrySet()){
            final String key = attributeValue.getKey();
            final String value = attributeValue.getValue();
            if (value == null) {
                _attributes.remove(key);
            } else {
                _attributes.put(key, value);
            }
        }
    }
}
