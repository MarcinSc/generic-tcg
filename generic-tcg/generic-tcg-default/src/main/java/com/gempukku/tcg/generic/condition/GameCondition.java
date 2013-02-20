package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameState;

public interface GameCondition {
    public boolean isMet(GameState gameState);
}
