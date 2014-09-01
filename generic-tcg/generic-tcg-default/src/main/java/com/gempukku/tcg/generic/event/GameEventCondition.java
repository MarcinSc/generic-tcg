package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameObjects;

public interface GameEventCondition {
    public boolean matches(GameObjects gameState, GameEvent event);
}
