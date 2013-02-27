package com.gempukku.tcg.generic.filter;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class GameObjectPropertyFilter implements GameObjectFilter {
    private String _property;
    private String _value;

    public void setProperty(String property) {
        _property = property;
    }

    public void setValue(String value) {
        _value = value;
    }

    @Override
    public boolean matches(GameState gameState, GameObject gameObject) {
        final String property = gameObject.getProperty(_property);
        return (property != null && property.equals(_value));
    }
}
