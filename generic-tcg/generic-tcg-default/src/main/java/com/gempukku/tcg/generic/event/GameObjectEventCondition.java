package com.gempukku.tcg.generic.event;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectEventCondition {
    public boolean matches(GameObjects gameState, DigitalObject conditionOn, GameEvent event);
}
