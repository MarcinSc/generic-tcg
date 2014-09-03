package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;
import com.gempukku.tcg.generic.condition.GameObjectCondition;

public class ConditionalEffect implements GameEffect {
    private GameObjectCondition _gameObjectCondition;
    private GameEffect _gameObjectEffect;

    public void setGameObjectCondition(GameObjectCondition gameObjectCondition) {
        _gameObjectCondition = gameObjectCondition;
    }

    public void setGameObjectEffect(GameEffect gameObjectEffect) {
        _gameObjectEffect = gameObjectEffect;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        if (_gameObjectCondition.isMet(gameObjects, context))
            return _gameObjectEffect.execute(gameObjects, context);
        else
            return new Result(null, false);
    }
}
