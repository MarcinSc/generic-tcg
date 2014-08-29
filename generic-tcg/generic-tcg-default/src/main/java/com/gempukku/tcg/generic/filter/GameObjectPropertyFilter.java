package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public class GameObjectPropertyFilter implements DigitalObjectFilter {
    private String _property;
    private String _value;

    public GameObjectPropertyFilter() {
    }

    public GameObjectPropertyFilter(String property, String value) {
        _property = property;
        _value = value;
    }

    public void setProperty(String property) {
        _property = property;
    }

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public boolean matches(GameState gameState, DigitalObject context, DigitalObject gameObject) {
        final String property = gameObject.getAttributes().get(_property);
        return (property != null && property.equals(_value));
    }
}
