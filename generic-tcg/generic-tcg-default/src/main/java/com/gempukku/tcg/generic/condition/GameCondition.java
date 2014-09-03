package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.generic.action.GameActionContext;

public abstract class GameCondition implements GameObjectCondition {
    @Override
    public final boolean isMet(GameObjects gameObjects, GameActionContext context) {
        return isMet(gameObjects);
    }

    public abstract boolean isMet(GameObjects gameState);
}
