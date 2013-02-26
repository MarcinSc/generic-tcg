package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public abstract class GameCondition implements GameObjectCondition {
    @Override
    public final boolean isMet(GameState gameState, GameObject gameObject) {
        return isMet(gameState);
    }

    public abstract boolean isMet(GameState gameState);
}
