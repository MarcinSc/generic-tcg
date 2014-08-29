package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameState;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectCondition {
    public boolean isMet(GameState gameState, DigitalObject digitalObject);
}
