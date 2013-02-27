package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.generic.object.GameObject;

public interface GameObjectEventCondition {
    public boolean matches(GameState gameState, GameObject conditionOn, GameEvent event);
}
