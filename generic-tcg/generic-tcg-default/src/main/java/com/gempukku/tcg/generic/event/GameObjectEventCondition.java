package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectEventCondition {
    public boolean matches(GameState gameState, DigitalObject conditionOn, GameEvent event);
}
