package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.condition.GameObjectCondition;
import com.gempukku.tcg.generic.object.GameObject;

public class ConditionalEffect implements GameObjectEffectSerie {
    private GameObjectCondition _gameObjectCondition;
    private GameObjectEffect _gameObjectEffect;

    public void setGameObjectCondition(GameObjectCondition gameObjectCondition) {
        _gameObjectCondition = gameObjectCondition;
    }

    public void setEffect(GameObjectEffect gameObjectEffect) {
        _gameObjectEffect = gameObjectEffect;
    }

    @Override
    public boolean execute(GameState gameState, GameObject gameObject) {
        if (_gameObjectCondition.isMet(gameState, gameObject))
            return _gameObjectEffect.execute(gameState, gameObject);
        else
            return false;
    }
}
