package com.gempukku.tcg.generic.condition;

import com.gempukku.tcg.GameObjects;
import com.gempukku.tcg.digital.DigitalObject;

public interface GameObjectCondition {
    public boolean isMet(GameObjects gameState, DigitalObject digitalObject);
}
