package com.gempukku.tcg.generic.conditions;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.Condition;

public class GameObjectEqualsCondition implements Condition {
    private String _gameObject;
    private String _value;

    public GameObjectEqualsCondition(String gameObject, String value) {
        _gameObject = gameObject;
        _value = value;
    }

    @Override
    public boolean isTrue(GameState gameState) {
        Object gameObject = gameState.getGameObject(_gameObject);
        return gameObject != null && gameObject.equals(_value);
    }
}
