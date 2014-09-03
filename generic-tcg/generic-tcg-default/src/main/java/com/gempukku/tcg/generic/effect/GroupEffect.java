package com.gempukku.tcg.generic.effect;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

import java.util.List;

public class GroupEffect implements GameEffect {
    private List<? extends GameEffect> _gameObjectEffects;

    public void setGameObjectEffects(List<? extends GameEffect> gameObjectEffects) {
        _gameObjectEffects = gameObjectEffects;
    }

    @Override
    public Result execute(GameObjects gameObjects, GameActionContext context) {
        for (GameEffect gameObjectEffect : _gameObjectEffects)
            gameObjectEffect.execute(gameObjects, context);

        return new Result(null, false);
    }
}
