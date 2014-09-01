package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public abstract class GameCondition implements GameObjectCondition {
    @Override
    public final boolean isMet(GameObjects gameState, DigitalObject digitalObject) {
        return isMet(gameState);
    }

    public abstract boolean isMet(GameObjects gameState);
}
