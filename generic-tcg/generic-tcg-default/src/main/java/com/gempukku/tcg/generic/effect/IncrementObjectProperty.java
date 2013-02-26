package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public class IncrementObjectProperty extends GameObjectEffect {
    private String _name;
    private Integer _max;
    private int _value;

    public void setName(String name) {
        _name = name;
    }

    public void setMax(int max) {
        _max = max;
    }

    public void setValue(int value) {
        _value = value;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObjectSource) {
        int newValue = _value+Integer.parseInt(gameObjectSource.getProperty(_name));
        if (_max != null)
            newValue = Math.min(_max, newValue);
        gameObjectSource.setProperty(_name, String.valueOf(newValue));
    }
}