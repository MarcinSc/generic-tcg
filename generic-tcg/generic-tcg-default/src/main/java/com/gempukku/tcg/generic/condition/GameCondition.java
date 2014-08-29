package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public abstract class GameCondition implements GameObjectCondition {
    @Override
    public final boolean isMet(GameState gameState, DigitalObject digitalObject) {
        return isMet(gameState);
    }

    public abstract boolean isMet(GameState gameState);
}
