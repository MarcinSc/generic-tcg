package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameState;

public interface GameEventCondition {
    public boolean matches(GameState gameState, GameEvent event);
}
