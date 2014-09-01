package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;
import com.gempukku.tcg.generic.condition.GameObjectCondition;

public class ConditionalEffect implements GameObjectEffectSerie {
    private GameObjectCondition _gameObjectCondition;
    private DigitalObjectEffect _gameObjectEffect;

    public void setGameObjectCondition(GameObjectCondition gameObjectCondition) {
        _gameObjectCondition = gameObjectCondition;
    }

    public void setEffect(DigitalObjectEffect gameObjectEffect) {
        _gameObjectEffect = gameObjectEffect;
    }

    @Override
    public boolean execute(GameObjects gameState, DigitalObject digitalObject) {
        if (_gameObjectCondition.isMet(gameState, digitalObject))
            return _gameObjectEffect.execute(gameState, digitalObject);
        else
            return false;
    }
}
