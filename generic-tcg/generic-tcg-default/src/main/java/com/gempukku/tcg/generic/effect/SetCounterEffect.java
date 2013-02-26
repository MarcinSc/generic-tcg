package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;
import com.gempukku.tcg.generic.other.Counter;

public class SetCounterEffect extends GameObjectEffect {
    private String _counterName;
    private int _value;

    public void setCounterName(String counterName) {
        _counterName = counterName;
    }

    public void setValue(int value) {
        _value = value;
    }

    @Override
    public void executeGameEffect(GameState gameState, GameObject gameObjectSource) {
        ((Counter) gameState.getGameObject(_counterName)).setValue(_value);
    }
}
